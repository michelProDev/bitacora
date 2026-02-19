-- ============================================================
-- Docker init script: creates the schema that Hibernate expects
-- and seeds the default users.
-- Runs only on first container start (empty volume).
-- ============================================================

CREATE DATABASE IF NOT EXISTS bitacoraData
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bitacoraData;

-- Tabla recursos (HR service)
CREATE TABLE IF NOT EXISTS recursos (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(255) UNIQUE,
    apellidos   VARCHAR(255),
    categoria_id BIGINT,
    tecnologia  VARCHAR(255),
    correo      VARCHAR(255) UNIQUE,
    password    VARCHAR(255),
    rol         VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed: admin (admin123) y usuario demo (user123)
INSERT IGNORE INTO recursos (nombre, apellidos, categoria_id, tecnologia, correo, password, rol)
VALUES ('Admin', 'Sistema', NULL, NULL, 'admin@btech.com',
        '$2b$10$ERg7kn3wTPkXM6oRgSlCReQY3rih/PoEJuQ/Nyelzkxyh4.3QgDQK',
        'ADMINISTRADOR');

INSERT IGNORE INTO recursos (nombre, apellidos, categoria_id, tecnologia, correo, password, rol)
VALUES ('Usuario', 'Demo', NULL, NULL, 'usuario@btech.com',
        '$2b$10$qQeGoZbLRirkKsVVncMffeW0MTMyg9E7rtADbintfYRIZkbPVOTHC',
        'USUARIO');
