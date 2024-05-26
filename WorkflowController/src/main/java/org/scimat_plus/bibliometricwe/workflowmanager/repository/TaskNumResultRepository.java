package org.scimat_plus.bibliometricwe.workflowmanager.repository;

import org.scimat_plus.bibliometricwe.workflowmanager.models.TaskNumResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskNumResultRepository extends MongoRepository<TaskNumResult,String> {
    @Query("{ 'projectId': ?0, 'taskLabel': ?1 }")
    Optional<List<TaskNumResult>> findByProjectIdAndTaskLabel(String projectId, String taskLabel);
}
