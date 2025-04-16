package com.example.app.models;

public class Registration implements BaseEntity{

    private static int idCounter = 1;
    private int id;

    private int userId;
    private int projectId;
    private RegistrationStatus status;

    public Registration() {
        this.id = idCounter++;
    }

    public Registration(int userId, int projectId, RegistrationStatus status) {
        this(); // call the no-arg constructor to generate the id
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
    }

    @Override
    public int getId() { return id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
}
