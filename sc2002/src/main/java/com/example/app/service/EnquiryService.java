package com.example.app.service;

import java.util.List;

import com.example.app.models.Enquiry;
import com.example.app.models.User;
import com.example.app.repository.EnquiryRepository;

public class EnquiryService extends GeneralService {

    private EnquiryRepository enquiryRepository = new EnquiryRepository();

    public EnquiryService(User user) {
        super(user); 
    }


    // Allow user to add Enquiry
    public int submitEnquiry(String question, int projectId) {
        Enquiry newEnquiry = new Enquiry(question, projectId, user.getId());
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


    // Get project based enquiries
    public List<Enquiry> getProjectEnquiries(int projectId) {
        return enquiryRepository.findByProjectId(projectId);
    }

    //Get single enquiry
    public Enquiry getEnquiry(int id) {
        return enquiryRepository.findById(id);
    }

    // Reply enquiry based on enquiry id
    // Save user respnse and responder id
    public void replyEnquiry(int id, String reply) {
        Enquiry enquiry = getEnquiry(id);
        enquiry.setResponse(reply);
        enquiry.setReplierId(user.getId());
    } 
    
}

