package com.example.app.controller;

import com.example.app.models.Applicant;
import com.example.app.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {
    
    @Autowired
    private ApplicantService applicantService;
    
    @GetMapping
    public List<Applicant> getAllApplicants() {
       return applicantService.findAllApplicants();
    }
    
    @GetMapping("/{id}")
    public Applicant getApplicant(@PathVariable Long id) {
       Optional<Applicant> applicantOpt = applicantService.findById(id);
       return applicantOpt.orElse(null);
    }
    
    @PostMapping
    public Applicant createApplicant(@RequestBody Applicant applicant) {
       return applicantService.createApplicant(applicant);
    }
    
    @DeleteMapping("/{id}")
    public void deleteApplicant(@PathVariable Long id) {
       applicantService.deleteApplicant(id);
    }
}
