package com.example.app.service.impl;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.EnquiryService;

import java.io.IOException;
import java.util.List;

public class EnquiryServiceImpl implements EnquiryService {

    private static final EnquiryRepository enquiryRepository = RepositoryDependency.getEnquiryRepository();

    @Override
    public Enquiry findById(Integer id) throws IOException {
        return enquiryRepository.findById(id);
    }

    @Override
    public List<Enquiry> findAll() throws IOException {
        return enquiryRepository.findAll();
    }

    @Override
    public List<Enquiry> findByProjectId(int projectId) throws IOException {
        return enquiryRepository.findByProjectId(projectId);
    }

    @Override
    public List<Enquiry> findByReplierId(int userId) throws IOException {
        return enquiryRepository.findByReplierId(userId);
    }

    @Override
    public List<Enquiry> findByEnquirerId(int userId) throws IOException {
        return enquiryRepository.findByEnquirerId(userId);
    }

    // Allow user to add Enquiry
    @Override
    public Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException {
        Enquiry newEnquiry = new Enquiry(null, question, projectId, userId);
        return enquiryRepository.save(newEnquiry);
    }

    // Allow user to edit Enquiry
    // Change is reflected in hashmap
    @Override
    public Enquiry updateEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        enquiry.setQuestion(question);
        return enquiryRepository.save(enquiry);
    }

    @Override
    public Enquiry replyEnquiry(Integer id, Integer replierId, String reply) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        if (enquiry == null) {
            throw new NullPointerException("Enquiry not found");
        }
        enquiry.setResponse(reply);
        enquiry.setReplierId(replierId);
        return enquiryRepository.save(enquiry);
    }

    // Delete enquiry
    @Override
    public void deleteEnquiry(Integer id) throws IOException {
        enquiryRepository.deleteById(id);
    }
}
