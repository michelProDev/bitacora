package com.btech.catalogo_micro.repository;

import com.btech.catalogo_micro.entity.EstadoTareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTareaEntity, Long> {
    Optional<EstadoTareaEntity> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
