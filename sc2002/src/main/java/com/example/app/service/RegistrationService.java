package com.example.app.service;


import java.io.IOException;
import java.util.List;

import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;
import com.example.app.repository.RegistrationRepository;

public class RegistrationService {
    
    private static RegistrationRepository registrationRepository = new RegistrationRepository();

    public RegistrationService() {

    }

    // Applicant can add registration as Officer
    public Registration registerForProject(int userId, int projectId, String projectName) throws IOException {
        Registration register = new Registration(null, userId, projectId, RegistrationStatus.PENDING);

        return registrationRepository.save(register);
    } 


    // Get registration by id 
    // Can be used by officer to see their registration
    public Registration getRegistrationByRegistrationId(int id) {
        return registrationRepository.findById(id);
    }

    // Get registration by project Id 
    public List<Registration>  getRegistrationsByProjectId(int projectId) throws IOException {
        return registrationRepository.findByProjectId(projectId);
    }    

    // Get all registrations for a project 
    public List<Registration> findByProjectId(int projectId) throws IOException {
        return registrationRepository.findByProjectId(projectId);
    }

    // Get all registrations for a user
    public void deleteRegistration(int id) throws IOException {
        registrationRepository.deleteById(id);
    }

    // Approve registration to become officer
    // Meant for maanagers
    public void changeStatus(int registerId, RegistrationStatus status) throws IOException {
        Registration registration = registrationRepository.findById(registerId);
        registration.setStatus(status);
        registrationRepository.save(registration);
    }

}
