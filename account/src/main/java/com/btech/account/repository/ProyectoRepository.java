package com.btech.account.repository;

import com.btech.account.entities.ProyectosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<ProyectosEntity, Long> {
    Optional<ProyectosEntity> findByNombreProyecto(String nombreProyecto);
    void deleteByNombreProyecto(String nombreProyecto);
}