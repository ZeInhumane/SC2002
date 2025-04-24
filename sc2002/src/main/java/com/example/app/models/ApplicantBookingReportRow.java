package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;

/**
 * Represents a row in the applicant booking report.
 *
 * <p>This class contains details about an applicant's booking, including:
 * <ul>
 *   <li>Applicant's name</li>
 *   <li>Applicant's age</li>
 *   <li>Marital status</li>
 *   <li>Project name</li>
 *   <li>Flat type</li>
 * </ul>
 *
 * <p>It is used to display summarized booking information in reports.
 */
public class ApplicantBookingReportRow {
    /**
     * The name of the applicant.
     */
    private final String applicantName;

    /**
     * The age of the applicant.
     */
    private final int applicantAge;

    /**
     * The marital status of the applicant.
     */
    private final MaritalStatus maritalStatus;

    /**
     * The name of the project the applicant has booked.
     */
    private final String projectName;

    /**
     * The type of flat booked by the applicant.
     */
    private final FlatType flatType;

    public ApplicantBookingReportRow(String applicantName, int applicantAge, MaritalStatus maritalStatus,
            String projectName, FlatType flatType) {
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
        return String.format("%-20s | Age: %-3d | %-8s | %-20s | %-7s", applicantName, applicantAge, maritalStatus,
                projectName, flatType);
    }
}
