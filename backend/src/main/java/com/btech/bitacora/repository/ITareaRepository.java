package com.btech.bitacora.repository;

import com.btech.bitacora.model.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITareaRepository extends JpaRepository<TareaEntity, Long> {
}
