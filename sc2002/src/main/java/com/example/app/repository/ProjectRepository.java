package com.example.app.repository;

import java.io.IOException;
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
                .filter(p -> Objects.equals(p.getGroup(), status) && Objects.equals(p.getVisibility(), visibility))
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

}
