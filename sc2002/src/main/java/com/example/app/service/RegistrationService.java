package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;

public interface RegistrationService {

    // Applicant can add registration as Officer
    Registration registerForProject(int userId, int projectId) throws IOException;

    // Approve registration to become officer
    // Meant for maanagers
    Registration changeStatus(int registerId, RegistrationStatus status) throws IOException;

    // Get registration by id
    // Can be used by officer to see their registration
    Registration findById(int id) throws IOException;

    // Get registration by project Id
    List<Registration> findByProjectId(int projectId) throws IOException;

    // Get all registrations for a user
    void deleteById(int id) throws IOException;

}
