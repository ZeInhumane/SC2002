package com.example.app.service;


import com.example.app.models.Officer;
import com.example.app.repository.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OfficerService {
    
    @Autowired
    private OfficerRepository officerRepository;
    
    public List<Officer> findAllOfficers() {
       return officerRepository.findAll();
    }
    
    public Officer createOfficer(Officer officer) {
       return officerRepository.save(officer);
    }
    
    public Optional<Officer> findById(Long id) {
       return officerRepository.findById(id);
    }
    
    public void deleteOfficer(Long id) {
       officerRepository.deleteById(id);
    }
}

