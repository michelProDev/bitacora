import React, { useCallback, useEffect, useRef, useState } from 'react';
import { Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

const SERVICES = [
  { id: 'discovery', label: 'Discovery', url: 'http://localhost:8761' },
  { id: 'account',   label: 'Account',   url: 'http://localhost:8080' },
  { id: 'crm',       label: 'CRM',       url: 'http://localhost:8081' },
  { id: 'hr',        label: 'HR',        url: 'http://localhost:8082' },
  { id: 'catalogos', label: 'Catálogos', url: 'http://localhost:8084' },
];

const POLL_INTERVAL = 5000;
const MAX_HISTORY = 60;

function ts() {
  return new Date().toLocaleTimeString('es', { hour12: false });
}

async function fetchJson(url, timeout = 4000) {
  const ctrl = new AbortController();
  const timer = setTimeout(() => ctrl.abort(), timeout);
  try {
    const res = await fetch(url, { signal: ctrl.signal });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    return await res.json();
  } finally {
    clearTimeout(timer);
  }
}

function parsePrometheus(text) {
  const metrics = {};
  for (const line of text.split('\n')) {
    if (line.startsWith('#') || !line.trim()) continue;
    const match = line.match(/^([a-zA-Z_:][a-zA-Z0-9_:{}=",.-]*)\s+([\d.eE+-]+|NaN|Inf|-Inf)$/);
    if (match) {
      metrics[match[1]] = parseFloat(match[2]);
    }
  }
  return metrics;
}

async function fetchPrometheus(url, timeout = 4000) {
  const ctrl = new AbortController();
  const timer = setTimeout(() => ctrl.abort(), timeout);
  try {
    const res = await fetch(`${url}/actuator/prometheus`, { signal: ctrl.signal });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    const text = await res.text();
    return parsePrometheus(text);
  } catch {
    return null;
  } finally {
    clearTimeout(timer);
  }
}

function extractMetric(prom, prefix) {
  if (!prom) return null;
  for (const [k, v] of Object.entries(prom)) {
    if (k.startsWith(prefix)) return v;
  }
  return null;
}

function extractQuantile(prom, metricBase, quantile) {
  if (!prom) return null;
  const q = String(quantile);
  for (const [k, v] of Object.entries(prom)) {
    if (k.startsWith(metricBase) && k.includes(`quantile="${q}"`)) return v;
  }
  return null;
}

function StatusBadge({ status }) {
  const cls = status === 'UP' ? 'badge up' : status === 'DOWN' ? 'badge down' : 'badge unknown';
  return <span className={cls}>{status}</span>;
}

function MiniChart({ data, dataKey, color, unit }) {
  if (!data || data.length < 2) return <span className="muted">Esperando datos...</span>;
  return (
    <ResponsiveContainer width="100%" height={90}>
      <AreaChart data={data} margin={{ top: 4, right: 4, bottom: 0, left: 0 }}>
        <defs>
          <linearGradient id={`grad-${dataKey}`} x1="0" y1="0" x2="0" y2="1">
            <stop offset="5%" stopColor={color} stopOpacity={0.3} />
            <stop offset="95%" stopColor={color} stopOpacity={0} />
          </linearGradient>
        </defs>
        <CartesianGrid strokeDasharray="3 3" stroke="#333" />
        <XAxis dataKey="time" tick={{ fontSize: 9, fill: '#888' }} interval="preserveStartEnd" />
        <YAxis tick={{ fontSize: 9, fill: '#888' }} width={40} />
        <Tooltip
          contentStyle={{ background: '#1e1e2e', border: '1px solid #444', borderRadius: 6, fontSize: 12 }}
          labelStyle={{ color: '#ccc' }}
          formatter={(v) => [`${typeof v === 'number' ? v.toFixed(2) : v} ${unit || ''}`, dataKey]}
        />
        <Area type="monotone" dataKey={dataKey} stroke={color} fill={`url(#grad-${dataKey})`} strokeWidth={2} dot={false} />
      </AreaChart>
    </ResponsiveContainer>
  );
}

function ServiceCard({ svc, health, info, prom, history }) {
  const status = health?.status ?? 'UNKNOWN';
  const db = health?.components?.db?.status ?? '-';
  const disk = health?.components?.diskSpace;
  const diskFree = disk?.details?.free ? (disk.details.free / 1073741824).toFixed(2) : '-';
  const diskTotal = disk?.details?.total ? (disk.details.total / 1073741824).toFixed(2) : '-';

  const jvmUsed = prom ? (extractMetric(prom, 'jvm_memory_used_bytes') ?? 0) / 1048576 : null;
  const jvmMax = prom ? (extractMetric(prom, 'jvm_memory_max_bytes') ?? 0) / 1048576 : null;
  const cpuUsage = prom ? extractMetric(prom, 'process_cpu_usage') ?? extractMetric(prom, 'system_cpu_usage') : null;
  const threadsLive = prom ? extractMetric(prom, 'jvm_threads_live_threads') : null;
  const httpTotal = prom ? extractMetric(prom, 'http_server_requests_seconds_count') : null;
  const uptimeSeconds = prom ? extractMetric(prom, 'process_uptime_seconds') : null;

  const p50 = extractQuantile(prom, 'http_server_requests_seconds', '0.5');
  const p90 = extractQuantile(prom, 'http_server_requests_seconds', '0.9');
  const p95 = extractQuantile(prom, 'http_server_requests_seconds', '0.95');
  const p99 = extractQuantile(prom, 'http_server_requests_seconds', '0.99');

  const fmtMs = (v) => v != null ? `${(v * 1000).toFixed(1)} ms` : '-';

  const uptime = uptimeSeconds != null
    ? `${Math.floor(uptimeSeconds / 3600)}h ${Math.floor((uptimeSeconds % 3600) / 60)}m`
    : '-';

  return (
    <div className={`service-card ${status === 'UP' ? 'card-up' : status === 'DOWN' ? 'card-down' : 'card-unknown'}`}>
      <div className="card-header">
        <h2>{svc.label}</h2>
        <StatusBadge status={status} />
      </div>
      <div className="card-meta">
        <span>{svc.url}</span>
        <span>Uptime: {uptime}</span>
      </div>

      <div className="metrics-grid">
        <div className="metric">
          <span className="metric-label">Base de Datos</span>
          <StatusBadge status={db} />
        </div>
        <div className="metric">
          <span className="metric-label">Disco Libre</span>
          <span className="metric-value">{diskFree} / {diskTotal} GB</span>
        </div>
        <div className="metric">
          <span className="metric-label">JVM Memoria</span>
          <span className="metric-value">{jvmUsed != null ? `${jvmUsed.toFixed(0)} MB` : '-'}{jvmMax != null && jvmMax > 0 ? ` / ${jvmMax.toFixed(0)} MB` : ''}</span>
        </div>
        <div className="metric">
          <span className="metric-label">CPU</span>
          <span className="metric-value">{cpuUsage != null ? `${(cpuUsage * 100).toFixed(1)}%` : '-'}</span>
        </div>
        <div className="metric">
          <span className="metric-label">Hilos Activos</span>
          <span className="metric-value">{threadsLive != null ? threadsLive : '-'}</span>
        </div>
        <div className="metric">
          <span className="metric-label">HTTP Requests</span>
          <span className="metric-value">{httpTotal != null ? Math.round(httpTotal) : '-'}</span>
        </div>
      </div>

      <div className="percentiles-section">
        <span className="section-title">Latencia HTTP (Percentiles)</span>
        <div className="percentiles-grid">
          <div className="pct-card">
            <span className="pct-label">P50</span>
            <span className="pct-value">{fmtMs(p50)}</span>
          </div>
          <div className="pct-card pct-highlight">
            <span className="pct-label">P90</span>
            <span className="pct-value">{fmtMs(p90)}</span>
          </div>
          <div className="pct-card">
            <span className="pct-label">P95</span>
            <span className="pct-value">{fmtMs(p95)}</span>
          </div>
          <div className="pct-card">
            <span className="pct-label">P99</span>
            <span className="pct-value">{fmtMs(p99)}</span>
          </div>
        </div>
      </div>

      <div className="charts-row">
        <div className="chart-box">
          <span className="chart-title">Memoria JVM (MB)</span>
          <MiniChart data={history} dataKey="jvmMb" color="#6c8eef" unit="MB" />
        </div>
        <div className="chart-box">
          <span className="chart-title">CPU (%)</span>
          <MiniChart data={history} dataKey="cpuPct" color="#f5a623" unit="%" />
        </div>
        <div className="chart-box chart-wide">
          <span className="chart-title">P90 Latencia HTTP (ms)</span>
          <MiniChart data={history} dataKey="p90ms" color="#e06cef" unit="ms" />
        </div>
      </div>
    </div>
  );
}

export default function App() {
  const [data, setData] = useState({});
  const historyRef = useRef({});

  const poll = useCallback(async () => {
    const results = {};
    await Promise.all(
      SERVICES.map(async (svc) => {
        let health = null;
        let info = null;
        let prom = null;
        try { health = await fetchJson(`${svc.url}/actuator/health`); } catch { health = { status: 'DOWN' }; }
        try { info = await fetchJson(`${svc.url}/actuator/info`); } catch { /* ignore */ }
        try { prom = await fetchPrometheus(svc.url); } catch { /* ignore */ }

        const jvmUsed = prom ? (extractMetric(prom, 'jvm_memory_used_bytes') ?? 0) / 1048576 : null;
        const cpuUsage = prom ? extractMetric(prom, 'process_cpu_usage') ?? extractMetric(prom, 'system_cpu_usage') : null;
        const p90val = extractQuantile(prom, 'http_server_requests_seconds', '0.9');

        const prev = historyRef.current[svc.id] ?? [];
        const point = {
          time: ts(),
          jvmMb: jvmUsed != null ? Math.round(jvmUsed) : null,
          cpuPct: cpuUsage != null ? +(cpuUsage * 100).toFixed(1) : null,
          p90ms: p90val != null ? +(p90val * 1000).toFixed(1) : null,
        };
        const next = [...prev, point].slice(-MAX_HISTORY);
        historyRef.current[svc.id] = next;

        results[svc.id] = { health, info, prom, history: next };
      })
    );
    setData({ ...results });
  }, []);

  useEffect(() => {
    poll();
    const id = setInterval(poll, POLL_INTERVAL);
    return () => clearInterval(id);
  }, [poll]);

  const upCount = SERVICES.filter((s) => data[s.id]?.health?.status === 'UP').length;

  return (
    <div className="app">
      <header className="topbar">
        <h1>Monitoring Dashboard</h1>
        <div className="topbar-stats">
          <span className="stat-pill">{upCount}/{SERVICES.length} servicios activos</span>
          <span className="stat-pill">Polling: {POLL_INTERVAL / 1000}s</span>
        </div>
      </header>
      <main className="grid">
        {SERVICES.map((svc) => (
          <ServiceCard
            key={svc.id}
            svc={svc}
            health={data[svc.id]?.health}
            info={data[svc.id]?.info}
            prom={data[svc.id]?.prom}
            history={data[svc.id]?.history ?? []}
          />
        ))}
      </main>
    </div>
  );
}
