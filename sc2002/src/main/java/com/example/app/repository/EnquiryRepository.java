package com.example.app.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Enquiry;

public class EnquiryRepository extends GeneralRepository<Enquiry> {

    public List<Enquiry> findByEnquirerId(int enquirerId) {
        return storage.values().stream()
                .filter(e -> e.getEnquirerId() == enquirerId)
                .collect(Collectors.toList());
    }

    public List<Enquiry> findByReplierId(int replierId) {
        return storage.values().stream()
                .filter(e -> e.getReplierId() == replierId)
                .collect(Collectors.toList());
    }

    public List<Enquiry> findByProjectId(int projectId) {
        return storage.values().stream()
                .filter(e -> e.getProjectId() == projectId)
                .collect(Collectors.toList());
    }
}

