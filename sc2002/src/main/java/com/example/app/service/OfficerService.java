package com.example.app.service;

import java.util.List;

import com.example.app.models.Enquiry;
import com.example.app.models.Officer;
import com.example.app.models.Registration;
import com.example.app.models.Project;


public class OfficerService extends ApplicantService {
    

    static RegistrationService registrationService = new RegistrationService();
    static UserManagementService userManagementService = new UserManagementService();

    public OfficerService(Officer user) {
        super(user);
    }

    // Check if Officer is officer for a project (After Officer Application)
    public boolean isOfficerFor(int projectId) {
        return projectService.isOfficerFor(user.getId(), projectId);
    }

    // Check if is applicant for HDB (Before Officer Application)
    public boolean isApplicantFor(int projectId) {
        return applicationService.isApplicantFor(user.getId(), projectId);
    }

    // Check if Project is still running
    public boolean isProjectStillApplying(int projectId) {
        return projectService.isProjectStillApplying(projectId);
    }

    // Checks if cannot register as officer (An applicant for the hdb or has a projhect somewhere before deadline)
    public boolean isNotRegisterableAsOfficer(int projectId) {
        Officer officer = (Officer) user; // Cast explicitly since user is declared as Applicant
        int oldProjectId = officer.getRegisteredProject();
        
        return isApplicantFor(projectId) || isProjectStillApplying(oldProjectId);
    }

    // Register for project
    public void registerAsOfficer(int projectId) {
        Officer officer = (Officer) user; // Cast explicitly since user is declared as Applicant

        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project ID " + projectId + " not found.");
        }

        if (officer.getRegisteredProject() != -1 ) {
            registrationService.deleteOfficerRegistration(officer.getRegisteredProject());
        }    

        // Override the registration to the latest one
        int registrationId = registrationService.registerAsOfficerForProject(officer.getId(), projectId, project.getProjectName());
        officer.setRegisteredProject(registrationId);

    }

    // Retrieve curren registration even if it is turned off
    public Registration viewCurrentRegistration() {
        Officer officer = (Officer) user; // Cast explicitly since user is declared as Applicant
        return registrationService.getRegistrationByRegistrationId(officer.getRegisteredProject());
    }

    // get Enquiries regarding a project
    public List<Enquiry> getProjectEnquiries() {
        
        Officer officer = (Officer) user;
        int projectId = officer.getRegisteredProject();
        if (projectId == -1) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return enquiryService.getEnquiriesByProjectId(projectId);
    }

    // get Enquiry using enquiry id
    public Enquiry getEnquiry(int id) {
        return enquiryService.getEnquiry(id);
    }

    // reply Enquiry 
    public void replyEnquiry(int id, String reply) {
        enquiryService.replyEnquiry(id, reply, user.getId());
    }
    

    //------------------------------
    //Additionally Required functions
    //------------------------------

    // Maybe get all bookings 

    // bookUser

}
