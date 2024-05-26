package org.scimat_plus.bibliometricwe.workflowmanager.service;

import org.scimat_plus.bibliometricwe.workflowmanager.models.WorkflowModel;
import org.scimat_plus.bibliometricwe.workflowmanager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDto {

    @Autowired
    public WorkflowRepository workflowRepository;

    public List<WorkflowModel> getTasks(){
        return workflowRepository.findAll();
    }

    public WorkflowModel createTask(WorkflowModel workflow){
        return workflowRepository.save(workflow);
    }

    public Optional<WorkflowModel> searchTaskById(String id){
        return workflowRepository.findById(id);
    }

    public WorkflowModel searchByTaskProjectName(String projectName) throws Exception {
        try{
            return workflowRepository.findByProject(projectName);
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    public WorkflowModel updateTask(String id, WorkflowModel workflow){
        workflow.setId(id);
        return workflowRepository.save(workflow);
    }

    public ResponseEntity< Optional<WorkflowModel>> executeTask(String id){
        Optional<WorkflowModel> optionalWorkflowModel = workflowRepository.findById(id);
        WorkflowModel workflowModel = optionalWorkflowModel.orElse(null);
        if(workflowModel != null) {
            workflowModel.setExecute(true);
            workflowRepository.save(workflowModel);
        }
        return new ResponseEntity<>(optionalWorkflowModel, HttpStatus.OK);

    }
    public void deleteTask(String id){
        workflowRepository.deleteById(id);
    }


}
