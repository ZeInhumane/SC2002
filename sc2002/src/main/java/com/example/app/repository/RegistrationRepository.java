package com.example.app.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Registration;

public class RegistrationRepository extends GeneralRepository<Registration> {

    public List<Registration> findByUserId(int userId) {
        return storage.values().stream()
                .filter(r -> r.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<Registration> findByProjectId(int projectId) {
        return storage.values().stream()
                .filter(r -> r.getProjectId() == projectId)
                .collect(Collectors.toList());
    }
}
