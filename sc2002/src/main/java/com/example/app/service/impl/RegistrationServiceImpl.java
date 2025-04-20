package com.example.app.service.impl;

import com.example.app.enums.RegistrationStatus;
import com.example.app.models.Registration;
import com.example.app.repository.RegistrationRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.RegistrationService;

import java.io.IOException;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository = RepositoryDependency.getRegistrationRepository();

    // Applicant can add registration as Officer
    public Registration registerForProject(int userId, int projectId) throws IOException {
        Registration register = new Registration(null, userId, projectId, RegistrationStatus.PENDING);

        return registrationRepository.save(register);
    }

    // Get registration by id
    // Can be used by officer to see their registration
    public Registration findById(int id) throws IOException {
        return registrationRepository.findById(id);
    }

    // Get all registrations for a project
    public List<Registration> findByProjectId(int projectId) throws IOException {
        return registrationRepository.findByProjectId(projectId);
    }

    // Get all registrations for a user
    public void deleteById(int id) throws IOException {
        registrationRepository.deleteById(id);
    }

    // Approve registration to become officer
    // Meant for maanagers
    public Registration changeStatus(int registerId, RegistrationStatus status) throws IOException {
        Registration registration = registrationRepository.findById(registerId);
        registration.setStatus(status);
        return registrationRepository.save(registration);
    }
}
