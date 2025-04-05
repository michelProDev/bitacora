package com.btech.bitacora.repository;

import com.btech.bitacora.model.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

}
