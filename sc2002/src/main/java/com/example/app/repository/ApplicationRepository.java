package com.example.app.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;

public class ApplicationRepository extends GeneralRepository<Application> {

    public Application findOneByUserId(int userId) {
        return storage.values().stream()
            .filter(a -> a.getUserId() == userId)
            .findFirst()
            .orElse(null);
    }

    public List<Application> findByStatus(ApplicationStatus status) {
        return storage.values().stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Application> findByProjectId(int projectId) {
        return storage.values().stream()
                .filter(a -> a.getProjectId() == projectId)
                .collect(Collectors.toList());
    }

    
}
