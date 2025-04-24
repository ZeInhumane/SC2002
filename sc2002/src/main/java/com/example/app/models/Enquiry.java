package com.example.app.models;

import com.example.app.service.ProjectService;
import com.example.app.service.impl.ProjectServiceImpl;
import com.example.app.models.Project;

/**
 * Enquiry class that represents an enquiry made by a user regarding a project. It implements the BaseEntity interface.
 */
public class Enquiry implements BaseEntity {

    /**
     * The ID of the enquiry.
     */
    private Integer id;

    /**
     * The question asked by the user.
     */
    private String question;

    /**
     * The response to the enquiry.
     */
    private String response;

    /**
     * The ID of the project for which the enquiry is made.
     */
    private Integer projectId;

    /**
     * The ID of the user who made the enquiry.
     */
    private Integer enquirerId;

    /**
     * The ID of the officer who replied to the enquiry.
     */
    private Integer replierId;

    private static ProjectService projectService = new ProjectServiceImpl();

    /**
     * Default constructor for the Enquiry class.
     */
    public Enquiry() {
    }

    /**
     * Constructor for the Enquiry class with parameters.
     *
     * @param id
     *            the ID of the enquiry. This field should be null for new enquiries. After saving to the database, it
     *            will be set.
     * @param question
     *            the question asked by the user
     * @param projectId
     *            the ID of the project for which the enquiry is made
     * @param enquirerId
     *            the ID of the user who made the enquiry
     */
    public Enquiry(Integer id, String question, Integer projectId, Integer enquirerId) {
        this.id = id;
        this.question = question;
        this.projectId = projectId;
        this.enquirerId = enquirerId;
    }

    /**
     * Gets the ID of the enquiry.
     * 
     * @return the ID of the enquiry
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the enquiry.
     * 
     * @param id
     *            the ID of the enquiry
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the question asked by the user.
     * 
     * @return the question asked by the user
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question asked by the user.
     * 
     * @param question
     *            the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the response to the enquiry.
     * 
     * @return the response to the enquiry
     */
    public String getResponse() {
        return response;
    }

    /**
     * Sets the response to the enquiry.
     * 
     * @param response
     *            the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Gets the ID of the project for which the enquiry is made.
     * 
     * @return the ID of the project for which the enquiry is made
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project for which the enquiry is made.
     * 
     * @param projectId
     *            the ID of the project to set
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the ID of the user who made the enquiry.
     * 
     * @return the ID of the user who made the enquiry
     */
    public Integer getEnquirerId() {
        return enquirerId;
    }

    /**
     * Sets the ID of the user who made the enquiry.
     * 
     * @param enquirerId
     *            the ID of the user to set
     */
    public void setEnquirerId(Integer enquirerId) {
        this.enquirerId = enquirerId;
    }

    /**
     * Gets the ID of the officer who replied to the enquiry.
     * 
     * @return the ID of the officer who replied to the enquiry
     */
    public Integer getReplierId() {
        return replierId;
    }

    /**
     * Sets the ID of the officer who replied to the enquiry.
     * 
     * @param replierId
     *            the ID of the officer to set
     */
    public void setReplierId(Integer replierId) {
        this.replierId = replierId;
    }

    /**
     * Returns a string representation of the enquiry.
     * @return a string representation of the enquiry
     */
    @Override
    public String toString() {
        String projectName = "Unknown";
        try {
            Project project = projectService.findById(projectId);
            if (project != null) {
                projectName = project.getProjectName();
            }
        } catch (Exception e) {
            projectName = "Error fetching project name";
        }

        return String.format(
                """
                Question: %s
                Response: %s
                Project ID: %d
                Project Name: %s
                """,
                question,
                response != null ? response : "N/A",
                projectId,
                projectName);
    }
}
