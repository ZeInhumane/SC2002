package com.example.app.service;


import com.example.app.models.Applicant;
import com.example.app.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService {
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    public List<Applicant> findAllApplicants() {
       return applicantRepository.findAll();
    }
    
    public Applicant createApplicant(Applicant applicant) {
       return applicantRepository.save(applicant);
    }
    
    public Optional<Applicant> findById(Long id) {
       return applicantRepository.findById(id);
    }
    
    public void deleteApplicant(Long id) {
       applicantRepository.deleteById(id);
    }
}

