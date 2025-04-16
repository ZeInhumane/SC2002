package com.example.app.models;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private int currentProjectId;
    private List<Project> pastProjects = new ArrayList<>();

    public Manager() { }

    public Manager(String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(username, password, email, role, nric, age, maritalStatus);
    }

    public int getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(int currentProjectId) {
        this.currentProjectId = currentProjectId;
    }

    public List<Project> getPastProjects() {
        return pastProjects;
    }

    public void setPastProjects(List<Project> pastProjects) {
        this.pastProjects = pastProjects;
    }

    public void addProject(Project project) {
        if (!pastProjects.contains(project)) {
            pastProjects.add(project);
        }
    }
}
