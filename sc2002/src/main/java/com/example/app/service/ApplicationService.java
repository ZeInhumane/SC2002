package com.example.app.service;

import com.example.app.models.Application;
import com.example.app.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    public List<Application> findAllApplications() {
       return applicationRepository.findAll();
    }
    
    public Application createApplication(Application application) {
       return applicationRepository.save(application);
    }
    
    public Optional<Application> findById(Long id) {
       return applicationRepository.findById(id);
    }
    
    public void deleteApplication(Long id) {
       applicationRepository.deleteById(id);
    }
}

