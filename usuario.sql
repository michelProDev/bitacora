-- Contraseña admin123 codificada con BCrypt (cost 10)
-- Contraseña user123 codificada con BCrypt (cost 10)
-- Nota: BCrypt usa salt aleatorio, no la clave JWT del properties.
-- La app usa BCryptPasswordEncoder que valida estos hashes.

INSERT INTO recursos (nombre, apellidos, categoria_id, tecnologia, correo, password, rol)
VALUES ('Admin', 'Sistema', NULL, NULL, 'admin@btech.com',
        '$2b$10$ERg7kn3wTPkXM6oRgSlCReQY3rih/PoEJuQ/Nyelzkxyh4.3QgDQK',
        'ADMINISTRADOR');

INSERT INTO recursos (nombre, apellidos, categoria_id, tecnologia, correo, password, rol)
VALUES ('Usuario', 'Demo', NULL, NULL, 'usuario@btech.com',
        '$2b$10$qQeGoZbLRirkKsVVncMffeW0MTMyg9E7rtADbintfYRIZkbPVOTHC',
        'USUARIO');
