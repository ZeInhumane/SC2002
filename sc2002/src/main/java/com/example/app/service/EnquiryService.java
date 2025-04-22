package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.models.Enquiry;

public interface EnquiryService {
    // Get single enquiry
    Enquiry findById(Integer id) throws IOException;

    // Get all enquiries for manager
    List<Enquiry> findAll() throws IOException;

    // For officer or manager to get all enquiries
    List<Enquiry> findByProjectId(int projectId) throws IOException;

    // For officer or manager to manage their responded enquiries
    List<Enquiry> findByReplierId(int replierId) throws IOException;

    // For applicant to manage their submitted enquiries
    List<Enquiry> findByEnquirerId(int enquirerId) throws IOException;

    // For applicant to add enquiry
    Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException;

    // For applicant to edit enquiry
    Enquiry updateEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException;

    // For officer or manager to add response
    // Only for enquiry that has not been responded
    Enquiry replyEnquiry(Integer id, Integer replierId, String response) throws IOException, NullPointerException;

    // for applicant to delete enquiry
    void deleteEnquiry(Integer id) throws IOException;

}
