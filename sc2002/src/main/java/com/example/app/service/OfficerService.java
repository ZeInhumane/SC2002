package com.example.app.service;

import java.io.IOException;
import java.util.List;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Officer;
import com.example.app.models.Registration;
import com.example.app.models.Project;

public interface OfficerService extends ApplicantService {

    boolean isRegistrable(Officer officer, int projectId) throws IOException, NullPointerException;

    List<Project> getRegistrableProjects(Officer officer) throws IOException, NullPointerException;

    Officer registerForProject(Officer officer, int projectId) throws IOException, NullPointerException;

    Registration viewCurrentRegistration(Officer officer) throws IOException, NullPointerException;

    Project viewCurrentProject(Officer officer) throws IOException, NullPointerException;

    List<Enquiry> getHandlingEnquiries(Officer officer) throws IOException, NullPointerException;

    Enquiry replyEnquiry(Officer officer, int enquiryId, String message) throws IOException, NullPointerException;

    List<Application> getHandlingApplications(Officer officer) throws IOException, NullPointerException;

    void bookFlatForApplicant(String nric) throws IOException, NullPointerException;

    String generateBookingReceipt(String nric) throws IOException, NullPointerException;
}
