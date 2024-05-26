package org.scimat_plus.bibliometricwe.workflowmanager.models;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workflow")
public class WorkflowModel {
    @Id
    private String id;
    private String user;
    private String project;
    private String workflow;
    private boolean execute = false;
    public WorkflowModel(String user, String project, String workflow) {
        this.user = user;
        this.project = project;
        this.workflow = workflow;
    }

    public WorkflowModel() {

    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }


}
