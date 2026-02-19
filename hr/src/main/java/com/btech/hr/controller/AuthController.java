package com.btech.hr.controller;

import com.btech.hr.entity.RecursoEntity;
import com.btech.hr.repository.RecursoRepository;
import com.btech.hr.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RecursoRepository recursoRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          RecursoRepository recursoRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.recursoRepository = recursoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String password = request.get("password");

        if (correo == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Se requieren correo y password"));
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, password)
            );

            String rol = auth.getAuthorities().iterator().next()
                    .getAuthority().replace("ROLE_", "");

            String token = jwtUtil.generateToken(auth.getName(), rol);

            RecursoEntity recurso = recursoRepository.findByCorreo(auth.getName())
                    .orElse(null);
            String nombre = recurso != null ? recurso.getNombre() : auth.getName();

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "type", "Bearer",
                    "correo", auth.getName(),
                    "nombre", nombre,
                    "rol", rol
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }
    }
}
