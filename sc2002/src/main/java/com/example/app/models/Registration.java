package com.example.app.models;

import com.example.app.enums.RegistrationStatus;

public class Registration implements BaseEntity{
    private Integer id;
    private Integer userId;
    private Integer projectId;
    private RegistrationStatus status;

    public Registration() {
    }

    public Registration(Integer id, Integer userId, Integer projectId, RegistrationStatus status) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }

    @Override
    public String toDisplay() {
        return String.format("""
                [Registration Id: %s]
                Project (%s):
                Application Status: %s
                """, id, projectId, status);
    }
}
