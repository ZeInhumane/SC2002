package com.example.app.models;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;

public class Application implements BaseEntity {

    private Integer id;
    private Integer userId;
    private Integer projectId;
    private ApplicationStatus status;
    private FlatType flatType;

    public Application() {
    }

    public Application(Integer id, Integer userId, Integer projectId, ApplicationStatus status, FlatType flatType) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
        this.flatType = flatType;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    @Override
    public String toDisplay() {
        return String.format("""
            [Application Id: %s]
            Project (%s):
            Application Status: %s
            """, id , projectId, status);
    }

}

