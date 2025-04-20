package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

public class Officer extends Applicant{

    private Integer registeredProject;

    public Officer() { }

    public Officer(Integer id, String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus, FlatType flatType, Integer applicationId, Integer registeredProject) {
        super(id, username, password, email, role, nric, age, maritalStatus, flatType, applicationId);
        this.registeredProject = registeredProject;
    }

    public void setRegisteredProject(Integer registerId) {
        this.registeredProject = registerId;
    }

    public Integer getRegisteredProject() {
        return registeredProject;
    }

    
}



