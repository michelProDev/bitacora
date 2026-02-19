package com.btech.hr.security;

import com.btech.hr.entity.RecursoEntity;
import com.btech.hr.repository.RecursoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RecursoRepository recursoRepository;

    public CustomUserDetailsService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        RecursoEntity recurso = recursoRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        return new User(
                recurso.getCorreo(),
                recurso.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + recurso.getRol().toUpperCase()))
        );
    }
}
