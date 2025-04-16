package com.example.app.models;

public class Applicant extends User {

    private FlatType flatType;

    public Applicant() { }

    public Applicant(String name, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(name, password, email, role, nric, age, maritalStatus);
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
}

