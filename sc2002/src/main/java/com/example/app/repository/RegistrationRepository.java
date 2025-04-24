package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Registration;
import com.example.app.serializer.SerializerDependency;

/**
 * Repository class for Registration. This class extends the GeneralRepository class to provide CRUD operations for
 * Registration objects.
 *
 * @see GeneralRepository
 * @see Registration
 */
public class RegistrationRepository extends GeneralRepository<Registration> {

    /**
     * Constructor for RegistrationRepository. Initializes the repository with the Registration serializer and the file
     * name.
     *
     * @see SerializerDependency
     * @see GeneralRepository
     */
    public RegistrationRepository() {
        super(SerializerDependency.getRegistrationSerializer(), "registrations.txt");
    }

    /**
     * Finds all registrations by the user ID.
     * 
     * @param userId
     *            the ID of the user
     * @return a list of registrations made by the user
     * @throws IOException
     *             if there is an error reading the file
     */
    public List<Registration> findByUserId(Integer userId) throws IOException {
        return this.findAll().stream().filter(r -> Objects.equals(r.getUserId(), userId)).collect(Collectors.toList());
    }

    /**
     * Finds all registrations by the project ID.
     * 
     * @param projectId
     *            the ID of the project
     * @return a list of registrations related to the project
     * @throws IOException
     *             if there is an error reading the file
     */
    public List<Registration> findByProjectId(Integer projectId) throws IOException {
        return this.findAll().stream().filter(r -> Objects.equals(r.getProjectId(), projectId))
                .collect(Collectors.toList());
    }
}
