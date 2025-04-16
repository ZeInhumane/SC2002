package com.example.app.models;

public class Application implements BaseEntity {

    private static int idCounter = 1; // shared across all Application instances

    private final int id;
    private int userId;
    private int projectId;
    private ApplicationStatus status;

    public Application() {
        this.id = idCounter++;
    }

    public Application(int userId, int projectId, ApplicationStatus status) {
        this.id = idCounter++;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

