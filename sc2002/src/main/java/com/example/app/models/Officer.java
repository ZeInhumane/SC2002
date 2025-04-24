package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

public class Officer extends Applicant {
    private Integer registrationId;
    private Integer projectId;

    public Officer() {
    }

    public Officer(Integer id, String username, String password, String email, Role role, String nric, int age,
            MaritalStatus maritalStatus, FlatType flatType, Integer applicationId, Integer registeredId,
            Integer projectId) {
        super(id, username, password, email, role, nric, age, maritalStatus, flatType, applicationId);
        this.registrationId = registeredId;
        this.projectId = projectId;
    }

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registeredId) {
        this.registrationId = registeredId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
