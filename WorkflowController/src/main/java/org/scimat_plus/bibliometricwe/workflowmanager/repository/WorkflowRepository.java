package org.scimat_plus.bibliometricwe.workflowmanager.repository;

import org.scimat_plus.bibliometricwe.workflowmanager.models.WorkflowModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends MongoRepository<WorkflowModel, String> {
    WorkflowModel findByProject(String projectName);
}
