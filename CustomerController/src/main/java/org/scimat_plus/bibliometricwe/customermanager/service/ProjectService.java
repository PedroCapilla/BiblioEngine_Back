package org.scimat_plus.bibliometricwe.customermanager.service;

import jakarta.transaction.Transactional;
import org.scimat_plus.bibliometricwe.customermanager.model.ProjectDto;
import org.scimat_plus.bibliometricwe.customermanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public List<ProjectDto> findAll(){
        return projectRepository.findAll();
    }

    @Transactional
    public Optional<ProjectDto> findById(Long id){return projectRepository.findById(id);}

    @Transactional
    public ProjectDto save(ProjectDto project){
        return projectRepository.save(project);
    }

    @Transactional
    public List<ProjectDto> findProjectsByCustomerId(Long customerId){
        return projectRepository.findProjectsByCustomerId(customerId);
    }

    @Transactional
    public void deleteProject(ProjectDto project){
        projectRepository.delete(project);
    }
}
