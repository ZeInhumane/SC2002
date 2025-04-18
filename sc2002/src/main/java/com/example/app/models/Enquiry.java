package com.example.app.models;

public class Enquiry implements BaseEntity {

    private static int counter = 1; // for manual ID generation

    private int id;
    private String question;
    private String response;
    private String projectName;
    private int projectId;
    private int enquirerId;
    private int replierId;

    public Enquiry() {
        this.id = counter++;
    }

    public Enquiry(String question, int projectId, int enquirerId, String projectName) {
        this.id = counter++;
        this.question = question;
        this.projectId = projectId;
        this.enquirerId = enquirerId;
        this.projectName = projectName;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEnquirerId() {
        return enquirerId;
    }

    public void setEnquirerId(int enquirerId) {
        this.enquirerId = enquirerId;
    }

    public int getReplierId() {
        return replierId;
    }

    public void setReplierId(int replierId) {
        this.replierId = replierId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return String.format("""
                Enquiry For Project: %s
                Question: %s
                Response: %s
                Project ID: %d
                """,
                projectName,
                question,
                response != null ? response : "N/A",
                projectId);
    }
}
