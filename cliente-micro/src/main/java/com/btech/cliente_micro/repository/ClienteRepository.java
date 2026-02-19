package com.btech.cliente_micro.repository;

import com.btech.cliente_micro.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByNombre(String nombre);
    void deleteByNombre(String nombre);
}