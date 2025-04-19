package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Registration;
import com.example.app.serializer.SerializerDependency;

public class RegistrationRepository extends GeneralRepository<Registration> {

    public RegistrationRepository() {
        super(SerializerDependency.getRegistrationSerializer(), "registrations.txt");
    }

    public List<Registration> findByUserId(Integer userId) throws IOException {
        return this.findAll().stream()
                .filter(r -> Objects.equals(r.getUserId(), userId))
                .collect(Collectors.toList());
    }

    public List<Registration> findByProjectId(Integer projectId) throws IOException{
        return this.findAll().stream()
                .filter(r -> Objects.equals(r.getProjectId(), projectId))
                .collect(Collectors.toList());
    }
}
