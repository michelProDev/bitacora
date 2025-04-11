package com.btech.bitacora.repository;

import com.btech.bitacora.model.entity.DesarrolladorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDesarrolladorRepository extends JpaRepository<DesarrolladorEntity, Long> {
}
