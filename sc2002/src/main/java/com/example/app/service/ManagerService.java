package com.example.app.service;

import java.io.IOException;
import java.util.*;

import com.example.app.models.*;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;


public interface ManagerService extends UserService{
    
    Project createProject(Manager manager,
                          String projectName,
                          Date applicationOpenDate,
                            Date applicationCloseDate,
                            String neighborhood,
                            boolean visibility,
                            Set<MaritalStatus> groups,
                            Map<FlatType, Integer> flats) throws IOException;

    Project updateProject(Manager manager,
                            int projectId,
                            String projectName,
                            Date applicationOpenDate,
                            Date applicationCloseDate,
                            String neighborhood,
                            boolean visibility,
                            Set<MaritalStatus> groups,
                            Map<FlatType, Integer> flats) throws IOException;

    boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException;

    Project toggleVisibility(Manager manager, int projectId) throws IOException;

    List<Project> getAllProjects() throws IOException;

    List<Project> getMyProjects(Manager manager) throws IOException;

    Project getHandlingProject(Manager manager) throws IOException;

    List<Registration> getRegistrationsOfCurrentProject(Manager manager) throws IOException;

    void updateRegistrationStatus(Manager manager, int registrationId, RegistrationStatus status) throws IOException;

    void deleteProject(int projectId) throws IOException;

    List<Application> getApplicationsOfProject(Manager manager, Integer projectId) throws IOException;

    Application updateApplicationStatus(int applicationId, boolean status) throws IOException;

    Application updateWithdrawalStatus(int applicationId, boolean status) throws IOException;

    List<Enquiry> getAllEnquiries() throws IOException;

    List<Enquiry> getEnquiriesOfProject(Manager manager, int projectId) throws IOException;

    Enquiry replyEnquiry(Manager manager, int enquiryId, String message) throws IOException;
}
