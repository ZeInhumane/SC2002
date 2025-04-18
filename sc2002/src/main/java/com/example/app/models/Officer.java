package com.example.app.models;

public class Officer extends Applicant implements ProjectAssignable{

    private int registeredProject = -1;

    public Officer() { }

    public Officer(String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(username, password, email, role, nric, age, maritalStatus);
    }

    @Override
    public void setRegisteredProject(int registerId) {
        this.registeredProject = registerId;
    }

    @Override
    public int getRegisteredProject() {
        return registeredProject;
    }

    
}



