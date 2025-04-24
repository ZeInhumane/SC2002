package com.example.app.service;

import java.io.IOException;
import java.util.List;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;

public interface ApplicationService {
    // Apply for a flat
    Application applyForProject(int applicantId, int projectId, FlatType flatType) throws IOException;

    // Get an application by id
    // For applicant to see their current application
    Application findById(int id) throws IOException;

    // Get all applications of a project
    // For officer and manager to see all applications
    List<Application> findByProjectId(int projectId) throws IOException;

    // For officer and manager to update status
    Application updateStatus(int id, ApplicationStatus status) throws IOException;

    // For applicant to withdraw application
    Application withdrawApplication(int id) throws IOException;

    // For manager to accept withdrawal
    Application updateWithdrawalStatus(int id, boolean status) throws IOException;

    // For manager to get all booked applications
    List<Application> getAllBookedApplications() throws IOException;

    Application save(Application application) throws IOException;
}
