package com.btech.hr.repository;

import com.btech.hr.entity.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {
    Optional<RecursoEntity> findByNombre(String nombre);
    Optional<RecursoEntity> findByCorreo(String correo);
    void deleteByNombre(String nombre);
}
