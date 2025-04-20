package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;
import com.example.app.serializer.Serializer;
import com.example.app.serializer.SerializerDependency;

public class ApplicationRepository extends GeneralRepository<Application> {

    public ApplicationRepository() {
        super(SerializerDependency.getApplicationSerializer(), "applications.txt");
    }


    public List<Application> findByUserId(Integer userId) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public List<Application> findByUserIdAndStatus(Integer userId, ApplicationStatus status) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getUserId(), userId) && Objects.equals(a.getStatus(), status))
                .collect(Collectors.toList());
    }

    public List<Application> findByProjectId(Integer projectId) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getProjectId(), projectId))
                .collect(Collectors.toList());
    }

    public List<Application> findByProjectIdAndRequestWithdrawal(Integer projectId, boolean requestWithdrawal) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getProjectId(), projectId) && Objects.equals(a.isRequestWithdrawal(), requestWithdrawal))
                .collect(Collectors.toList());
    }

    public List<Application> findByStatus(ApplicationStatus status) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getStatus(), status))
                .collect(Collectors.toList());
    }



}
