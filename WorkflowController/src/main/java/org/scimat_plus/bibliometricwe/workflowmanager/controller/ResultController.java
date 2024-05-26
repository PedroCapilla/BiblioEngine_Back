package org.scimat_plus.bibliometricwe.workflowmanager.controller;

import org.scimat_plus.bibliometricwe.workflowmanager.models.TaskNumResult;
import org.scimat_plus.bibliometricwe.workflowmanager.service.TaskResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ResultController {

    @Autowired
    private TaskResultService taskResultService;

    @GetMapping("/{projectId}/{taskLabel}")
    public List<TaskNumResult> getTaskByProjectIdAndLabel(@PathVariable String projectId, @PathVariable String taskLabel){
        System.out.println("Entrando");
        List<TaskNumResult> resultado = taskResultService.getTaskByProjectIdAndLabel(projectId, taskLabel);
        System.out.println(projectId);
        for(int i = 0; i<resultado.size(); i++){
            System.out.println(resultado.get(i).getResult());
        }
        return taskResultService.getTaskByProjectIdAndLabel(projectId, taskLabel);
    }
}
