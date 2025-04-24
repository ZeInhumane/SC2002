package com.example.app.models;

import java.util.*;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.exceptions.OfficerAlreadyInsideException;
import com.example.app.exceptions.OfficerLimitExceededException;

/**
 * Represents a project in the system. This class implements the BaseEntity interface. It contains project details such
 * as ID, name, application dates, neighborhood, manager ID, visibility, officer limit, officers, groups, and flat
 * count. Provides methods to manage officers, groups, and flats.
 *
 * @see BaseEntity
 * @see FlatType
 * @see MaritalStatus
 */
public class Project implements BaseEntity {

    /**
     * The ID of the project.
     */
    private Integer id;

    /**
     * The name of the project.
     */
    private String projectName;

    /**
     * The date when the application opens.
     */
    private Date applicationOpenDate;

    /**
     * The date when the application closes.
     */
    private Date applicationCloseDate;

    /**
     * The neighborhood where the project is located.
     */
    private String neighborhood;

    /**
     * The ID of the manager responsible for the project.
     */
    private Integer managerId;

    /**
     * The visibility status of the project.
     */
    private Boolean visibility;

    /**
     * The limit on the number of officers that can be assigned to the project.
     */
    private Integer officerLimit;

    /**
     * The set of officer IDs assigned to the project. A set is used to ensure unique officer IDs.
     */
    private Set<Integer> officers;

    /**
     * The set of marital status groups that the project is open to.
     */
    private Set<MaritalStatus> groups;

    /**
     * The map of flat types and their corresponding counts available in the project.
     */
    private Map<FlatType, Integer> flatCount;

    /**
     * Default constructor for the Project class. This constructor initializes the officers and groups sets, and the
     * flatCount map.
     */
    public Project() {
        this.groups = new HashSet<>();
        this.officers = new HashSet<>();
        this.flatCount = new HashMap<>();
    }

    /**
     * Constructor for the Project class with parameters.
     * 
     * @param id
     *            the ID of the project. This field should be null for new projects. After saving to the database, it
     *            will be set.
     * @param projectName
     *            the name of the project. This should be unique
     * @param applicationOpenDate
     *            the date when the application opens
     * @param applicationCloseDate
     *            the date when the application closes
     * @param neighborhood
     *            the neighborhood where the project is located
     * @param managerId
     *            the ID of the manager responsible for the project
     * @param visibility
     *            the visibility status of the project
     * @param officerLimit
     *            the limit on the number of officers that can be assigned to the project
     * @param officers
     *            the set of officer IDs assigned to the project
     * @param groups
     *            the set of marital status groups that the project is open to
     * @param flatCount
     *            the map of flat types and their corresponding counts available in the project
     */
    public Project(Integer id, String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, Integer managerId, Boolean visibility, Integer officerLimit, Set<Integer> officers,
            Set<MaritalStatus> groups, Map<FlatType, Integer> flatCount) {
        this.id = id;
        this.projectName = projectName;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.neighborhood = neighborhood;
        this.managerId = managerId;
        this.visibility = visibility;
        this.officerLimit = officerLimit;
        this.groups = groups;
        this.officers = officers;
        this.flatCount = flatCount;
    }

    /**
     * Gets the ID of the project.
     * 
     * @return the ID of the project
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the project.
     * 
     * @param id
     *            the ID of the project
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the project.
     * 
     * @return the name of the project
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     * 
     * @param projectName
     *            the name of the project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the date when the application opens.
     * 
     * @return the date when the application opens
     */
    public Date getApplicationOpenDate() {
        return applicationOpenDate;
    }

    /**
     * Sets the date when the application opens.
     * 
     * @param applicationOpenDate
     *            the date when the application opens
     */
    public void setApplicationOpenDate(Date applicationOpenDate) {
        this.applicationOpenDate = applicationOpenDate;
    }

    /**
     * Gets the date when the application closes.
     * 
     * @return the date when the application closes
     */
    public Date getApplicationCloseDate() {
        return applicationCloseDate;
    }

    /**
     * Sets the date when the application closes.
     * 
     * @param applicationCloseDate
     *            the date when the application closes
     */
    public void setApplicationCloseDate(Date applicationCloseDate) {
        this.applicationCloseDate = applicationCloseDate;
    }

    /**
     * Gets the neighborhood where the project is located.
     * 
     * @return the neighborhood where the project is located
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood where the project is located.
     * 
     * @param neighborhood
     *            the neighborhood where the project is located
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the limit on the number of officers that can be assigned to the project.
     * 
     * @return the limit on the number of officers
     */
    public Integer getOfficerLimit() {
        return officerLimit;
    }

    /**
     * Sets the limit on the number of officers that can be assigned to the project.
     * 
     * @param officerLimit
     *            the limit on the number of officers
     */
    public void setOfficerLimit(Integer officerLimit) {
        this.officerLimit = officerLimit;
    }

    /**
     * Gets the set of officer IDs assigned to the project.
     * 
     * @throws OfficerAlreadyInsideException
     *             if the officer is already inside the project
     * @throws OfficerLimitExceededException
     *             if the officer limit is exceeded
     */
    public void addOfficer(Integer officerId) throws OfficerAlreadyInsideException, OfficerLimitExceededException {
        if (officers.contains(officerId)) {
            throw new OfficerAlreadyInsideException("Officer " + officerId + " is already inside the project.");
        }
        if (officers.size() >= officerLimit) {
            throw new OfficerLimitExceededException("Officer limit exceeded for project " + id);
        }
        officers.add(officerId);
    }

    /**
     * Removes an officer from the project.
     * 
     * @param officerId
     *            the ID of the officer to be removed
     */
    public void removeOfficer(Integer officerId) {
        officers.remove(officerId);
    }

    /**
     * Gets the set of officer IDs assigned to the project.
     * 
     * @return the set of officer IDs
     */
    public Set<Integer> getOfficers() {
        return officers;
    }

    /**
     * Sets the set of officer IDs assigned to the project.
     * 
     * @param officers
     *            the set of officer IDs
     */
    public void setOfficers(Set<Integer> officers) {
        this.officers = officers;
    }

    /**
     * Gets the set of marital status groups that the project is open to.
     */
    public void addMaritalStatus(MaritalStatus group) throws UnsupportedOperationException {
        this.groups.add(group);
    }

    /**
     * Removes a marital status group from the project.
     * 
     * @param group
     *            the marital status group to be removed
     */
    public void removeMaritalStatus(MaritalStatus group) throws UnsupportedOperationException {
        this.groups.remove(group);
    }

    /**
     * Gets the set of marital status groups that the project is open to.
     * 
     * @return the set of marital status groups
     */
    public Set<MaritalStatus> getGroups() {
        return groups;
    }

    /**
     * Sets the set of marital status groups that the project is open to.
     * 
     * @param groups
     *            the set of marital status groups
     */
    public void setGroups(Set<MaritalStatus> groups) {
        this.groups = groups;
    }

    /**
     * Gets the visibility status of the project.
     * 
     * @return the visibility status of the project
     */
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility status of the project.
     * 
     * @param visibility
     *            the visibility status of the project
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Gets the ID of the manager responsible for the project.
     * 
     * @return the ID of the manager
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * Sets the ID of the manager responsible for the project.
     * 
     * @param managerId
     *            the ID of the manager
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * Gets the map of flat types and their corresponding counts available in the project.
     * 
     * @throws UnsupportedOperationException
     *             to ensure the flatCount is mutable
     * @throws IllegalArgumentException
     *             if the flat type is not available in this project
     */
    public void decrementFlatCount(FlatType flatType) throws UnsupportedOperationException {
        if (!flatCount.containsKey(flatType)) {
            throw new IllegalArgumentException("Flat type " + flatType + " is not available in this project.");
        }

        int currentCount = flatCount.get(flatType);
        if (currentCount <= 0) {
            throw new IllegalStateException("No flats of type " + flatType + " are left to allocate.");
        }
        flatCount.put(flatType, currentCount - 1);
    }

    /**
     * Checks if there are flats of the specified type available in the project.
     * 
     * @param flatType
     *            the type of flat to check
     * @return true if flats of the specified type are available, false otherwise
     */
    public boolean hasFlatLeft(FlatType flatType) {
        return flatCount.getOrDefault(flatType, 0) > 0;
    }

    /**
     * Gets the map of flat types and their corresponding counts available in the project.
     * 
     * @return the map of flat types and their counts
     */
    public Map<FlatType, Integer> getFlats() {
        return flatCount;
    }

    /**
     * Sets the map of flat types and their corresponding counts available in the project.
     * 
     * @param flatCount
     *            the map of flat types and their counts
     */
    public void setFlats(Map<FlatType, Integer> flatCount) {
        this.flatCount = flatCount;
    }

    /**
     * Sets the map of flat types and their corresponding counts available in the project.
     * @param flatCount the map of flat types and their counts
     */
    @Override
    public String toString() {
        return String.format("""
                ----------------------------------------
                [Project Id: %s]
                ~~~~~~~~~~~~~~~~~
                Name: %s 
                Application Period: %s to %s
                Neighborhood: %s
                For Group: %s
                Flats: %s
                Visibility: %s
                ManagerId: %s
                -----------------------------------------
                """,
                id,
                projectName,
                formatDate(applicationOpenDate),
                formatDate(applicationCloseDate),
                neighborhood,
                formatGroup(),
                formatFlats(),
                visibility ? "ON" : "OFF", managerId);
    }

    /**
     * Formats the date to a string in the format "yyyy-MM-dd".
     * 
     * @param date
     *            the date to be formatted
     * @return the formatted date string
     */
    public String formatDate(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * Formats the groups to a string.
     * 
     * @return the formatted groups string
     */
    public String formatGroup() {
        if (groups == null || groups.isEmpty())
            return "None";
        StringBuilder sb = new StringBuilder();
        for (MaritalStatus group : groups) {
            sb.append(group).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * Formats the flats to a string.
     * 
     * @return the formatted flats string
     */
    public String formatFlats() {
        if (flatCount == null || flatCount.isEmpty())
            return "None";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<FlatType, Integer> entry : flatCount.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units, ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
