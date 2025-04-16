package com.example.app.models;

public class Registration {

    private static long idCounter = 1;
    private long id;

    private User user;
    private Project project;
    private RegistrationStatus status;

    public Registration() {
        this.id = idCounter++;
    }

    public Registration(User user, Project project, RegistrationStatus status) {
        this(); // call the no-arg constructor to generate the id
        this.user = user;
        this.project = project;
        this.status = status;
    }

    public long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
}
