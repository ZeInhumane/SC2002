package com.example.app.service;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService {
    
    @Autowired
    private EnquiryRepository enquiryRepository;
    
    public List<Enquiry> findAllEnquiries() {
       return enquiryRepository.findAll();
    }
    
    public Enquiry createEnquiry(Enquiry enquiry) {
       return enquiryRepository.save(enquiry);
    }
    
    public Optional<Enquiry> findById(Long id) {
       return enquiryRepository.findById(id);
    }
    
    public void deleteEnquiry(Long id) {
       enquiryRepository.deleteById(id);
    }
}

