package com.example.app.service.impl;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.EnquiryService;

import java.io.IOException;
import java.util.List;

public class EnquiryServiceImpl implements EnquiryService {

    private static final EnquiryRepository enquiryRepository = RepositoryDependency.getEnquiryRepository();

    public Enquiry findById(Integer id) throws IOException {
        return enquiryRepository.findById(id);
    }

    public List<Enquiry> findAll() throws IOException {
        return enquiryRepository.findAll();
    }

    public List<Enquiry> findByProjectId(int projectId) throws IOException {
        return enquiryRepository.findByProjectId(projectId);
    }

    public List<Enquiry> findByReplierId(int userId) throws IOException {
        return enquiryRepository.findByReplierId(userId);
    }

    public List<Enquiry> findByEnquirerId(int userId) throws IOException {
        return enquiryRepository.findByEnquirerId(userId);
    }

    // Allow user to add Enquiry
    public Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException {
        Enquiry newEnquiry = new Enquiry(null, question, projectId, userId);
        return enquiryRepository.save(newEnquiry);
    }

    // Allow user to edit Enquiry
    // Change is reflected in hashmap
    public Enquiry updateEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setQuestion(question);
        return enquiryRepository.save(enquiry);
    }


    public Enquiry replyEnquiry(Integer id, Integer replierId, String reply) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setResponse(reply);
        enquiry.setReplierId(replierId);
        return enquiryRepository.save(enquiry);
    }

    // Delete enquiry
    public void deleteEnquiry(Integer id) throws IOException {
        enquiryRepository.deleteById(id);
    }
}
