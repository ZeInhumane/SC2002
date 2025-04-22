package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

public class Applicant extends User {

    private FlatType flatType;
    private Integer applicationId;

    public Applicant() {
        super();
    }

    public Applicant(Integer id, String name, String password, String email, Role role, String nric, int age,
            MaritalStatus maritalStatus, FlatType flatType, Integer applicationId) {
        super(id, name, password, email, role, nric, age, maritalStatus);
        this.flatType = flatType;
        this.applicationId = applicationId;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toDisplay() {
        return String.format("""
                [Applicant ID: %d]
                Name: %s
                NRIC: %s
                Age: %d
                Marital Status: %s
                Email: %s
                Application ID: %d
                Flat Type: %s
                """,
                getId(),
                getName(),
                getNric(),
                getAge(),
                getMaritalStatus(),
                getEmail(),
                applicationId,
                flatType != null ? flatType.name() : "None"
        );
    }

}
