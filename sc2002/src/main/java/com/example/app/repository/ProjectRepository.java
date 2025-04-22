package com.example.app.repository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.serializer.SerializerDependency;

public class ProjectRepository extends GeneralRepository<Project> {

    public ProjectRepository() {
        super(SerializerDependency.getProjectSerializer(), "projects.txt");
    }

    public List<Project> findByMaritalStatusAndVisibility(MaritalStatus status, boolean visibility) throws IOException {
        return this.findAll().stream()
                .filter(p -> p.getGroups().contains(status) && Objects.equals(p.getVisibility(), visibility))
                .collect(Collectors.toList());
    }

    public List<Project> findByMaritalStatusAndVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(
            MaritalStatus status, boolean visibility, Date date) throws IOException {
        return this.findAll().stream()
                .filter(p -> p.getGroups().contains(status) && Objects.equals(p.getVisibility(), visibility)
                        && p.getApplicationOpenDate().compareTo(date) <= 0
                        && p.getApplicationCloseDate().compareTo(date) >= 0)
                .collect(Collectors.toList());
    }

    public List<Project> findByVisibility(boolean visibility) throws IOException{
        return this.findAll().stream()
                .filter(p -> Objects.equals(p.getVisibility(), visibility))
                .collect(Collectors.toList());
    }

    public List<Project> findByManagerId(Integer managerId) throws IOException{
        return this.findAll().stream()
                .filter(p -> Objects.equals(p.getManagerId(), managerId))
                .collect(Collectors.toList());
    }

    public Project findByManagerIdAndOpenDateGreaterThanAndCloseDateLessThan(Integer managerId, Date date) throws IOException {
        return this.findAll().stream()
                .filter(p -> Objects.equals(p.getManagerId(), managerId) && p.getApplicationOpenDate().compareTo(date) >= 0
                        && p.getApplicationCloseDate().compareTo(date) <= 0)
                .findFirst()
                .orElse(null);
    }

}
