package org.scimat_plus.bibliometricwe.workflowmanager.service;

import org.scimat_plus.bibliometricwe.workflowmanager.models.TaskNumResult;
import org.scimat_plus.bibliometricwe.workflowmanager.repository.TaskNumResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class TaskResultService {

    @Autowired
    private TaskNumResultRepository taskResultRepository;

    public List<TaskNumResult> getTaskByProjectIdAndLabel(String projectId, String taskLabel) {
        return taskResultRepository.findByProjectIdAndTaskLabel(projectId, taskLabel)
                .orElse(null);
    }


}
