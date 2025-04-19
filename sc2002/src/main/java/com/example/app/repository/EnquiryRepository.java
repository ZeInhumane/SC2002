package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Enquiry;
import com.example.app.serializer.SerializerDependency;

public class EnquiryRepository extends GeneralRepository<Enquiry> {

    public EnquiryRepository() {
        super(SerializerDependency.getEnquirySerializer(), "enquiries.txt");
    }

    public List<Enquiry> findByEnquirerId(int enquirerId) throws IOException {
        return this.findAll().stream()
                .filter(e -> Objects.equals(e.getEnquirerId(), enquirerId))
                .collect(Collectors.toList());
    }

    public List<Enquiry> findByReplierId(int replierId) throws IOException{
        return this.findAll().stream()
                .filter(e -> Objects.equals(e.getReplierId(), replierId))
                .collect(Collectors.toList());
    }

    public List<Enquiry> findByProjectId(int projectId) throws IOException{
        return this.findAll().stream()
                .filter(e -> Objects.equals(e.getProjectId(), projectId))
                .collect(Collectors.toList());
    }
}

