package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.models.Enquiry;

/**
 * Service interface for managing enquiries.
 */
public interface EnquiryService {

    /**
     * Find an enquiry by its ID.
     * 
     * @param id
     *            the ID of the enquiry
     * @return the found enquiry
     * @throws IOException
     *             if an I/O error occurs
     */
    Enquiry findById(Integer id) throws IOException;

    /**
     * Find all enquiries.
     * 
     * @return a list of all enquiries
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Enquiry> findAll() throws IOException;

    /**
     * Find all enquiries for a specific project.
     * 
     * @param projectId
     *            the ID of the project
     * @return a list of enquiries for the project
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Enquiry> findByProjectId(int projectId) throws IOException;

    /**
     * Find all enquiries for a specific replier.
     * 
     * @param replierId
     *            the ID of the replier
     * @return a list of enquiries for the replier
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Enquiry> findByReplierId(int replierId) throws IOException;

    /**
     * Find all enquiries for a specific enquirer.
     * 
     * @param enquirerId
     *            the ID of the enquirer
     * @return a list of enquiries for the enquirer
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Enquiry> findByEnquirerId(int enquirerId) throws IOException;

    /**
     * Find all enquiries for a specific project and enquirer.
     * 
     * @param projectId
     *            the ID of the project
     * @param enquirerId
     *            the ID of the enquirer
     * @return a list of enquiries for the project and enquirer
     * @throws IOException
     *             if an I/O error occurs
     */
    Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException;

    /**
     * Update an enquiry.
     * 
     * @param id
     *            the ID of the enquiry
     * @param question
     *            the new question
     * @return the updated enquiry
     * @throws IOException
     *             if an I/O error occurs
     */
    Enquiry updateEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException;

    /**
     * Reply to an enquiry. Only for enquiries that have not been replied to yet.
     * 
     * @param id
     *            the ID of the enquiry
     * @param replierId
     *            the ID of the replier
     * @param response
     *            the response to the enquiry
     * @return the updated enquiry
     * @throws IOException
     *             if an I/O error occurs
     */
    Enquiry replyEnquiry(Integer id, Integer replierId, String response) throws IOException, NullPointerException;

    /**
     * Delete an enquiry by its ID.
     * 
     * @param id
     *            the ID of the enquiry
     * @throws IOException
     *             if an I/O error occurs
     */
    void deleteEnquiry(Integer id) throws IOException;

}
