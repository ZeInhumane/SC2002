package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;

public class ApplicantBookingReportRow {
    private final String applicantName;
    private final int applicantAge;
    private final MaritalStatus maritalStatus;
    private final String projectName;
    private final FlatType flatType;

    public ApplicantBookingReportRow(String applicantName, int applicantAge, MaritalStatus maritalStatus, String projectName, FlatType flatType) {
        this.applicantName = applicantName;
        this.applicantAge = applicantAge;
        this.maritalStatus = maritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public int getApplicantAge() {
        return applicantAge;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    @Override
    public String toString() {
        return String.format("%-20s | Age: %-3d | %-8s | %-20s | %-7s", applicantName, applicantAge, maritalStatus, projectName, flatType);
    }
}
