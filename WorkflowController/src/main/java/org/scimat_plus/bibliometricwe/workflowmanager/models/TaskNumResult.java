package org.scimat_plus.bibliometricwe.workflowmanager.models;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "results")
public class TaskNumResult {

    @Id
    private String id;
    private String projectId;
    private String taskLabel;
    private String taskType;
    private Double result;
    private String AuthorKeywords;
    private String Authors;
    private String Citations;
    private String Countries;
    private String Journal;
    private String Organizations;
    private String SourceId;

    public String getAuthorKeywords() {
        return AuthorKeywords;
    }

    public void setAuthorKeywords(String authorKeywords) {
        AuthorKeywords = authorKeywords;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getCitations() {
        return Citations;
    }

    public void setCitations(String citations) {
        Citations = citations;
    }

    public String getCountries() {
        return Countries;
    }

    public void setCountries(String countries) {
        Countries = countries;
    }

    public String getJournal() {
        return Journal;
    }

    public void setJournal(String journal) {
        Journal = journal;
    }

    public String getOrganizations() {
        return Organizations;
    }

    public void setOrganizations(String organizations) {
        Organizations = organizations;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getSourceKeywords() {
        return SourceKeywords;
    }

    public void setSourceKeywords(String sourceKeywords) {
        SourceKeywords = sourceKeywords;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    private String SourceKeywords;
    private String Title;
    private Integer Year;

    public TaskNumResult(String projectId, String taskLabel, String taskType, Double result) {
        this.projectId = projectId;
        this.taskLabel = taskLabel;
        this.taskType = taskType;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskLabel() {
        return taskLabel;
    }

    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }


}
