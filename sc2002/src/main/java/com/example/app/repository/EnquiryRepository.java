package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Enquiry;
import com.example.app.serializer.SerializerDependency;

/**
 * Repository class for Enquiry.
 * This class extends the GeneralRepository class to provide CRUD operations for Enquiry objects.
 *
 * @see GeneralRepository
 * @see Enquiry
 */
public class EnquiryRepository extends GeneralRepository<Enquiry> {

    /**
     * Constructor for EnquiryRepository.
     * Initializes the repository with the Enquiry serializer and the file name.
     *
     * @see SerializerDependency
     * @see GeneralRepository
     */
    public EnquiryRepository() {
        super(SerializerDependency.getEnquirySerializer(), "enquiries.txt");
    }

    /**
     * Finds all enquiries by the enquirer ID.
     * @param enquirerId the ID of the enquirer
     * @return a list of enquiries made by the enquirer
     * @throws IOException if there is an error reading the file
     */
    public List<Enquiry> findByEnquirerId(int enquirerId) throws IOException {
        return this.findAll().stream().filter(e -> Objects.equals(e.getEnquirerId(), enquirerId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all enquiries by the replier ID.
     * @param replierId the ID of the replier
     * @return a list of enquiries replied by the replier
     * @throws IOException if there is an error reading the file
     */
    public List<Enquiry> findByReplierId(int replierId) throws IOException {
        return this.findAll().stream().filter(e -> Objects.equals(e.getReplierId(), replierId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all enquiries by the project ID.
     * @param projectId the ID of the project
     * @return a list of enquiries related to the project
     * @throws IOException if there is an error reading the file
     */
    public List<Enquiry> findByProjectId(int projectId) throws IOException {
        return this.findAll().stream().filter(e -> Objects.equals(e.getProjectId(), projectId))
                .collect(Collectors.toList());
    }
}
