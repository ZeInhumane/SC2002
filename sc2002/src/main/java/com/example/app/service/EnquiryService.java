package com.example.app.service;

import java.util.List;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;

public class EnquiryService {

    private static EnquiryRepository enquiryRepository = new EnquiryRepository();

    public EnquiryService() {

    }

    
    // Allow user to add Enquiry
    public int submitEnquiry(String question, int projectId, int userId) {
        Enquiry newEnquiry = new Enquiry(question, projectId, userId);
        Enquiry enquiry = enquiryRepository.save(newEnquiry);
        return enquiry.getId();
    }

    // Allow user to edit Enquiry
    // Change is refelcted in hashmap
    public void editEnquiry(int id, String question) {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setQuestion(question);
    }

    // Delete enquiry
    public void deleteEnquiry(int id) {
        enquiryRepository.deleteById(id);
    }

    // Delete all enquiries related to a project
    public void deleteEnquiriesByProjectId(int projectId) {
        List<Enquiry> enquiries = enquiryRepository.findByProjectId(projectId);
        for (Enquiry enquiry : enquiries) {
            enquiryRepository.deleteById(enquiry.getId());
        }
    }

    // Get project based enquiries
    public List<Enquiry> getEnquiriesByProjectId(int projectId) {
        return enquiryRepository.findByProjectId(projectId);
    }

    //Get single enquiry
    public Enquiry getEnquiry(int id) {
        return enquiryRepository.findById(id);
    }

    // Reply enquiry based on enquiry id
    // Save user respnse and responder id
    public void replyEnquiry(int id, String reply, int replierId) {
        Enquiry enquiry = getEnquiry(id);
        enquiry.setResponse(reply);
        enquiry.setReplierId(replierId);
    } 
    
}

