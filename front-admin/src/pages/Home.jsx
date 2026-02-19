import React from 'react';
import { ENV } from '../api/env.js';

export default function Home() {
  return (
    <div className="card">
      <div className="headerRow">
        <h1 className="h1">Microservices Admin</h1>
        <span className="kbd">Vite + React</span>
      </div>
      <p className="muted">
        Configure service URLs via <span className="kbd">.env</span>. This UI will call each microservice directly.
      </p>

      <div className="row" style={{ marginTop: 12 }}>
        <div className="card" style={{ gridColumn: 'span 12' }}>
          <div className="muted">Current base URLs</div>
          <pre style={{ margin: 0, whiteSpace: 'pre-wrap' }}>{JSON.stringify(ENV, null, 2)}</pre>
        </div>
      </div>
    </div>
  );
}
