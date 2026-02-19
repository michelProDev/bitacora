package com.btech.catalogo_micro.repository;

import com.btech.catalogo_micro.entity.TipoRegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoRegistroRepository extends JpaRepository<TipoRegistroEntity, Long> {
    Optional<TipoRegistroEntity> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}
