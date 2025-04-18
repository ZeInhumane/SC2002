package com.example.app.models;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private int currentProjectId;
    private List<Integer> pastProjects = new ArrayList<>();

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

    public List<Integer> getPastProjects() {
        return pastProjects;
    }

    public void setPastProjects(List<Integer> pastProjects) {
        this.pastProjects = pastProjects;
    }

    public void addProject(int projectId) {
        if (!pastProjects.contains(projectId)) {
            pastProjects.add(projectId);
        }
    }

    public void removeProject(int projectId) {
        if (currentProjectId == projectId) {
            currentProjectId = -1; 
        }
        pastProjects.remove(Integer.valueOf(projectId));
    }
    

}
