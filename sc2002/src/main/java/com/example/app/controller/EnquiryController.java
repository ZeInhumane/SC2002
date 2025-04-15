package com.example.app.controller;

import com.example.app.models.Enquiry;
import com.example.app.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {
    
    @Autowired
    private EnquiryService enquiryService;
    
    @GetMapping
    public List<Enquiry> getAllEnquiries() {
       return enquiryService.findAllEnquiries();
    }
    
    @GetMapping("/{id}")
    public Enquiry getEnquiry(@PathVariable Long id) {
       Optional<Enquiry> enquiryOpt = enquiryService.findById(id);
       return enquiryOpt.orElse(null);
    }
    
    @PostMapping
    public Enquiry createEnquiry(@RequestBody Enquiry enquiry) {
       return enquiryService.createEnquiry(enquiry);
    }
    
    @DeleteMapping("/{id}")
    public void deleteEnquiry(@PathVariable Long id) {
       enquiryService.deleteEnquiry(id);
    }
}

