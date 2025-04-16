package com.example.app.models;

public class Applicant extends User {

    private int applicationId;
    private FlatType flatType;

    public Applicant() { }

    public Applicant(String name, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(name, password, email, role, nric, age, maritalStatus);
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public FlatType flatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
}

