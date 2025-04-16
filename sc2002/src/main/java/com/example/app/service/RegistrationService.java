package com.example.app.service;


import com.example.app.models.Registration;
import com.example.app.models.RegistrationStatus;
import com.example.app.models.User;
import com.example.app.repository.RegistrationRepository;

public class RegistrationService extends GeneralService {
    
    private RegistrationRepository registrationRepository = new RegistrationRepository();

    public RegistrationService(User user) {
        super(user); 
    }


    // Applicant can add registration as Officer
    public int registerAsOfficerForProject(int projectId) {
        Registration register = new Registration(
            user.getId(), projectId, RegistrationStatus.PENDING
        );

        Registration registration = registrationRepository.save(register);

        return registration.getId();
    } 


    // Get registration by id 
    // Can be used by officer to see their registration
    public Registration getRegistrationByRegistrationId(int id) {
        return registrationRepository.findById(id);
    }

}
