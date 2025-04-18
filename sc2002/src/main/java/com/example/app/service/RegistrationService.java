package com.example.app.service;


import java.util.List;

import com.example.app.models.Registration;
import com.example.app.models.RegistrationStatus;
import com.example.app.repository.RegistrationRepository;

public class RegistrationService {
    
    private static RegistrationRepository registrationRepository = new RegistrationRepository();

    public RegistrationService() {

    }

    // Applicant can add registration as Officer
    public int registerAsOfficerForProject(int userId, int projectId, String projectName) {
        Registration register = new Registration(
            userId, projectId, RegistrationStatus.PENDING, projectName
        );

        Registration registration = registrationRepository.save(register);

        return registration.getId();
    } 


    // Get registration by id 
    // Can be used by officer to see their registration
    public Registration getRegistrationByRegistrationId(int id) {
        return registrationRepository.findById(id);
    }

    // Get registration by project Id 
    public List<Registration>  getRegistrationsByProjectId(int projectId) {
        return registrationRepository.findByProjectId(projectId);
    }    

    // Get all registrations for a project 
    public List<Registration> findByProjectId(int projectId) {
        return registrationRepository.findByProjectId(projectId);
    }

    // Withdraw Registration 
    // Can be used as a withdraw from BTO as application is always there
    public void deleteRegistration(int id) {
        registrationRepository.deleteById(id);
    }

    // Approve registration to become officer
    // Meant for maanagers
    public void approveOfficerRegistration(int registerId) {
        registrationRepository.findById(registerId).setStatus(RegistrationStatus.APPROVED);
    }

    // Delete Officer registration
    public void deleteOfficerRegistration(int registerId) {
        registrationRepository.deleteById(registerId);
    }

}
