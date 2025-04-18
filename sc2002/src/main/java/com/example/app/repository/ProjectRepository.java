package com.example.app.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Project;

public class ProjectRepository extends GeneralRepository<Project> {

    public List<Project> findByMaritalStatusAndVisibility(MaritalStatus status, boolean visibility) {
        return storage.values().stream()
                .filter(p -> p.getGroup() == status && p.getVisibility() == visibility)
                .collect(Collectors.toList());
    }

    public List<Project> findByVisibility(boolean visibility) {
        return storage.values().stream()
                .filter(p -> p.getVisibility() == visibility)
                .collect(Collectors.toList());
    }

    public List<Project> findByManagerId(int managerId) {
        return storage.values().stream()
                .filter(p -> Integer.valueOf(managerId).equals(p.getManagerId()))
                .collect(Collectors.toList());
    }

}
