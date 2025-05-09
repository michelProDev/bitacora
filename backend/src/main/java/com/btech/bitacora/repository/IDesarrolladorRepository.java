package com.btech.bitacora.repository;

import com.btech.bitacora.model.entity.DesarrolladorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDesarrolladorRepository extends JpaRepository<DesarrolladorEntity, Long> {
}
