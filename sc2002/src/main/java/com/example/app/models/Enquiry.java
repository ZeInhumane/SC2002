package com.example.app.models;

public class Enquiry implements BaseEntity {

    private Integer id;
    private String question;
    private String response;
    private Integer projectId;
    private Integer enquirerId;
    private Integer replierId;

    public Enquiry() {
    }

    public Enquiry(Integer id, String question, Integer projectId, Integer enquirerId) {
        this.id = id;
        this.question = question;
        this.projectId = projectId;
        this.enquirerId = enquirerId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEnquirerId() {
        return enquirerId;
    }

    public void setEnquirerId(Integer enquirerId) {
        this.enquirerId = enquirerId;
    }

    public Integer getReplierId() {
        return replierId;
    }

    public void setReplierId(Integer replierId) {
        this.replierId = replierId;
    }

    @Override
    public String toDisplay() {
        return String.format("""
                Question: %s
                Response: %s
                Project ID: %d
                """,
//                projectName,
                question,
                response != null ? response : "N/A",
                projectId);
    }
}
