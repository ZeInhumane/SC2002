package com.example.app.models;

import java.util.ArrayList;
import java.util.List;

public class Officer extends Applicant {

    private List<Project> projects = new ArrayList<>();

    public Officer() { }

    public Officer(String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(username, password, email, role, nric, age, maritalStatus);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (!projects.contains(project)) {
            projects.add(project);
        }
    }
}


