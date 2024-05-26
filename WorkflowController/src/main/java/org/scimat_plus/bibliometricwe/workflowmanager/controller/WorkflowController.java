package org.scimat_plus.bibliometricwe.workflowmanager.controller;


import org.scimat_plus.bibliometricwe.workflowmanager.models.WorkflowModel;
import org.scimat_plus.bibliometricwe.workflowmanager.repository.WorkflowRepository;
import org.scimat_plus.bibliometricwe.workflowmanager.service.ServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class WorkflowController {

    @Autowired
    private ServiceDto workflowService;



    @GetMapping
    public List<WorkflowModel> getTasks(){
        return workflowService.getTasks();
    }
    @PostMapping
    public WorkflowModel create(@RequestBody WorkflowModel workflow){
        return workflowService.createTask(workflow);
    }
//    @GetMapping("/{id}")
//    public Optional<WorkflowModel> searchById(@PathVariable String id) {
//        return workflowService.searchTaskById(id);
//    }
    @PutMapping("/{id}")
    public WorkflowModel update(@PathVariable String id, @RequestBody WorkflowModel workflow){
        return workflowService.updateTask(id, workflow);
    }

    @GetMapping("/{projectName}")
    public WorkflowModel searchByProjectName(@PathVariable String projectName) throws Exception {
        return workflowService.searchByTaskProjectName(projectName);
    }

    @GetMapping("/{id}/execute")
    public ResponseEntity< Optional<WorkflowModel>> executeTask(@PathVariable String id){
        return  workflowService.executeTask(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        workflowService.deleteTask(id);
    }


}
