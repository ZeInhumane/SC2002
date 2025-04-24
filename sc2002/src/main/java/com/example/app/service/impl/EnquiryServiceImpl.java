package com.example.app.service.impl;

import com.example.app.models.Enquiry;
import com.example.app.repository.EnquiryRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.EnquiryService;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the EnquiryService interface. This class handles the enquiry-related operations.
 *
 * @see EnquiryService
 */
public class EnquiryServiceImpl implements EnquiryService {

    /**
     * Repository for managing enquiries.
     *
     * @see EnquiryRepository
     */
    private static final EnquiryRepository enquiryRepository = RepositoryDependency.getEnquiryRepository();

    /**
     * Find an enquiry by ID
     * 
     * @param id
     *            the ID of the enquiry
     * @return the enquiry object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Enquiry findById(Integer id) throws IOException {
        return enquiryRepository.findById(id);
    }

    /**
     * Get all enquiries Meant for manager use only
     * 
     * @return the list of enquiries
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Enquiry> findAll() throws IOException {
        return enquiryRepository.findAll();
    }

    /**
     * Get all enquiries relating to a project
     * 
     * @param projectId
     *            the ID of the project
     * @return the list of enquiries
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Enquiry> findByProjectId(int projectId) throws IOException {
        return enquiryRepository.findByProjectId(projectId);
    }

    /**
     *
     * @param userId
     *            the ID of the replier
     * @return the list of enquiries
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Enquiry> findByReplierId(int userId) throws IOException {
        return enquiryRepository.findByReplierId(userId);
    }

    /**
     * Get all enquiries made by the enquirer This is meant for the enquirer to view their own enquiries
     * 
     * @param userId
     *            the ID of the enquirer
     * @return
     * @throws IOException
     */
    @Override
    public List<Enquiry> findByEnquirerId(int userId) throws IOException {
        return enquiryRepository.findByEnquirerId(userId);
    }

    /**
     * Submit an enquiry
     * 
     * @param question
     *            the question to be submitted
     * @param projectId
     *            the ID of the project
     * @param userId
     *            the ID of the enquirer
     * @return the enquiry object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Enquiry submitEnquiry(String question, Integer projectId, Integer userId) throws IOException {
        Enquiry newEnquiry = new Enquiry(null, question, projectId, userId);
        return enquiryRepository.save(newEnquiry);
    }

    /**
     * Allow user to edit the enquiry question In practice, the user is only allowed to modify their question if no
     * reply has been made
     * 
     * @param id
     *            the ID of the enquiry
     * @param question
     *            the new question
     * @return the updated enquiry object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the enquiry does not exist
     */
    @Override
    public Enquiry updateEnquiryQuestion(Integer id, String question) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);

        if (enquiry == null) {
            throw new NullPointerException("Enquiry not found");
        }
        enquiry.setQuestion(question);
        return enquiryRepository.save(enquiry);
    }

    /**
     * Reply to an enquiry
     * 
     * @param id
     *            the ID of the enquiry
     * @param replierId
     *            the ID of the replier
     * @param reply
     *            the response to the enquiry
     * @return the updated enquiry object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the enquiry does not exist
     */
    @Override
    public Enquiry replyEnquiry(Integer id, Integer replierId, String reply) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryRepository.findById(id);
        if (enquiry == null) {
            throw new NullPointerException("Enquiry not found");
        }
        enquiry.setResponse(reply);
        enquiry.setReplierId(replierId);
        return enquiryRepository.save(enquiry);
    }

    /**
     * Delete an enquiry
     * 
     * @param id
     *            the ID of the enquiry
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public void deleteEnquiry(Integer id) throws IOException {
        enquiryRepository.deleteById(id);
    }
}
