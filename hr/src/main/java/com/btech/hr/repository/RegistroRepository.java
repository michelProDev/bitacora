package com.btech.hr.repository;

import com.btech.hr.entity.RegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroEntity, Long> {
}
