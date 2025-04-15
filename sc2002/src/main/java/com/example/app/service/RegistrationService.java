package com.example.app.service;


import com.example.app.models.Registration;
import com.example.app.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    public List<Registration> findAllRegistrations() {
       return registrationRepository.findAll();
    }
    
    public Registration createRegistration(Registration registration) {
       return registrationRepository.save(registration);
    }
    
    public Optional<Registration> findById(Long id) {
       return registrationRepository.findById(id);
    }
    
    public void deleteRegistration(Long id) {
       registrationRepository.deleteById(id);
    }
}
