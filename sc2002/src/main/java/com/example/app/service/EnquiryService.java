package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;

public class EnquiryService {

    private static EnquiryRepository enquiryRepository = new EnquiryRepository();

    public EnquiryService() {

    }

    // Allow user to add Enquiry
    public Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException {
        Enquiry newEnquiry = new Enquiry(null, question, projectId, userId);
        return enquiryRepository.save(newEnquiry);
    }

    // Allow user to edit Enquiry
    // Change is reflected in hashmap
    public void editEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setQuestion(question);
        enquiryRepository.save(enquiry);
    }

    public void editEnquiryResponse(Integer id, String response) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setResponse(response);
        enquiryRepository.save(enquiry);
    }

    // Delete enquiry
    public void deleteEnquiry(Integer id) throws IOException {
        enquiryRepository.deleteById(id);
    }

    // Get project based enquiries
    public List<Enquiry> getEnquiriesByProjectId(int projectId) throws IOException {
        return enquiryRepository.findByProjectId(projectId);
    }

    // Get project based enquiries
    public List<Enquiry> getEnquiriesByUserId(int userId) throws IOException {
        return enquiryRepository.findByProjectId(userId);
    }

    //Get single enquiry
    public Enquiry getEnquiry(Integer id) throws IOException{
        return enquiryRepository.findById(id);
    }

    // Reply enquiry based on enquiry id
    // Save user response and responder id
    public void replyEnquiry(Integer id, Integer replierId, String reply) throws IOException, NullPointerException {
        Enquiry enquiry = getEnquiry(id);
        enquiry.setResponse(reply);
        enquiry.setReplierId(replierId);
    }
    
}

