package com.example.app.models;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private Integer currentProjectId;
    private List<Integer> pastProjects = new ArrayList<>();

    public Manager() { }

    public Manager(Integer id, String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus, Integer currentProjectId) {
        super(id, username, password, email, role, nric, age, maritalStatus);
        this.currentProjectId = currentProjectId;
    }

    public Integer getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(Integer currentProjectId) {
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

    public void removeProject(Integer projectId) {
        if (currentProjectId.equals(projectId)) {
            currentProjectId = null;
        }
        pastProjects.remove(projectId);
    }
    

}
