package com.example.app.controller;

import com.example.app.models.Registration;
import com.example.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @GetMapping
    public List<Registration> getAllRegistrations() {
       return registrationService.findAllRegistrations();
    }
    
    @GetMapping("/{id}")
    public Registration getRegistration(@PathVariable Long id) {
       Optional<Registration> regOpt = registrationService.findById(id);
       return regOpt.orElse(null);
    }
    
    @PostMapping
    public Registration createRegistration(@RequestBody Registration registration) {
       return registrationService.createRegistration(registration);
    }
    
    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable Long id) {
       registrationService.deleteRegistration(id);
    }
}

