package com.example.app.models;

public class Registration implements BaseEntity{

    private static int idCounter = 1;
    private int id;

    private int userId;
    private int projectId;
    private RegistrationStatus status;
    private String projectName;

    public Registration() {
        this.id = idCounter++;
    }

    public Registration(int userId, int projectId, RegistrationStatus status, String projectName) {
        this(); // call the no-arg constructor to generate the id
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
        this.projectName = projectName;
    }

    @Override
    public int getId() { return id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("""
                [Registration Id: %s]
                Project (%s): %s
                Application Status: %s
                """, id , projectId, projectName, status);
    }
}
