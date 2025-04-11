package com.btech.bitacora.repository;

import com.btech.bitacora.model.entity.ProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoRepository extends JpaRepository<ProyectoEntity, Long> {

}
