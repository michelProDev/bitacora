package com.btech.catalogo_micro.repository;

import com.btech.catalogo_micro.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    Optional<CategoriaEntity> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
