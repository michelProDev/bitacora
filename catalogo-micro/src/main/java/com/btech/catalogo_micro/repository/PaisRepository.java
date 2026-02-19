package com.btech.catalogo_micro.repository;

import com.btech.catalogo_micro.entity.PaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<PaisEntity, Long> {
    Optional<PaisEntity> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
