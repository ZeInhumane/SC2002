package com.example.app.models;

public class Application {

    private static long idCounter = 1; // shared across all Application instances

    private Long id;
    private User user;
    private Project project;
    private ApplicationStatus status;

    public Application() {
        this.id = idCounter++;
    }

    public Application(User user, Project project, ApplicationStatus status) {
        this.id = idCounter++;
        this.user = user;
        this.project = project;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    // No setter for ID to keep it auto-managed
    public User getApplicant() {
        return user;
    }

    public void setApplicant(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

