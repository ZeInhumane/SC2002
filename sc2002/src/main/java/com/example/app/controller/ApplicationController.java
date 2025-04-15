package com.example.app.controller;

import com.example.app.models.Application;
import com.example.app.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping
    public List<Application> getAllApplications() {
       return applicationService.findAllApplications();
    }
    
    @GetMapping("/{id}")
    public Application getApplication(@PathVariable Long id) {
       Optional<Application> appOpt = applicationService.findById(id);
       return appOpt.orElse(null);
    }
    
    @PostMapping
    public Application createApplication(@RequestBody Application application) {
       return applicationService.createApplication(application);
    }
    
    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
       applicationService.deleteApplication(id);
    }
}

