package org.scimat_plus.bibliometricwe.customermanager.repository;

import org.scimat_plus.bibliometricwe.customermanager.model.CustomerDto;
import org.scimat_plus.bibliometricwe.customermanager.model.ProjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectDto, Long> {

    @Query("SELECT p FROM ProjectDto p WHERE p.customerId = :customerId")
    List<ProjectDto> findProjectsByCustomerId(@Param("customerId") Long customerId);


}
