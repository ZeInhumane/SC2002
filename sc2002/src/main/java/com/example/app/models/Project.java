package com.example.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.app.exceptions.OfficerAlreadyInsideException;
import com.example.app.exceptions.OfficerLimitExceededException;

public class Project implements BaseEntity {

    private static int idCounter = 1; // for auto-incrementing IDs
    private int id;

    private String projectName;
    private Date applicationOpenDate;
    private Date applicationCloseDate;
    private String neighborhood;
    private MaritalStatus group;
    private Boolean visibility;
    private Map<FlatType, Integer> flats;

    private int managerId;
    private List<Integer> officers = new ArrayList<>();

    public Project() {
        this.id = idCounter++;
    }

    public Project(String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood,
            int managerId, MaritalStatus group, Boolean visibility, Map<FlatType, Integer> flats) {
        this();
        this.projectName = projectName;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.neighborhood = neighborhood;
        this.managerId = managerId;
        this.group = group;
        this.visibility = visibility;
        this.flats = flats;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getApplicationOpenDate() {
        return applicationOpenDate;
    }

    public void setApplicationOpenDate(Date applicationOpenDate) {
        this.applicationOpenDate = applicationOpenDate;
    }

    public Date getApplicationCloseDate() {
        return applicationCloseDate;
    }

    public void setApplicationCloseDate(Date applicationCloseDate) {
        this.applicationCloseDate = applicationCloseDate;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public MaritalStatus getGroup() {
        return group;
    }

    public void setGroup(MaritalStatus group) {
        this.group = group;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManager(int managerId) {
        this.managerId = managerId;
    }

    public List<Integer> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Integer> officers) {
        this.officers = officers;
    }

    public void addOfficer(int officerId) {
        if (officers.size() >= 10) {
            throw new OfficerLimitExceededException("Cannot assign more than 10 offciers to this project");
        }

        if (officers.contains(officerId)) {
            throw new OfficerAlreadyInsideException("Cannot assign to project when officer is already inside");
        }

        officers.add(officerId);

    }

    public void removeOfficer(int officerId) {
        if (!officers.remove(Integer.valueOf(officerId))) {
            throw new IllegalArgumentException("Officer ID " + officerId + " not found in this project.");
        }
    }

    public void decrementFlatCount(FlatType flatType) {
        if (!flats.containsKey(flatType)) {
            throw new IllegalArgumentException("Flat type " + flatType + " is not available in this project.");
        }

        int currentCount = flats.get(flatType);
        if (currentCount <= 0) {
            throw new IllegalStateException("No flats of type " + flatType + " are left to allocate.");
        }

        flats.put(flatType, currentCount - 1);
    }

    public boolean hasFlatLeft(FlatType flatType) {
        return flats.getOrDefault(flatType, 0) > 0;
    }

    public Map<FlatType, Integer> getFlats() {
        return flats;
    }

    public void setFlats(Map<FlatType, Integer> flats) {
        this.flats = flats;
    }

    @Override
    public String toString() {
        return String.format("""
                [Project ID: %d]
                Name: %s
                Neighborhood: %s
                Group: %s
                Application Period: %s to %s
                Flats: %s
                Visibility: %s
                Officers Assigned: %d
                """,
                id,
                projectName,
                neighborhood,
                group,
                formatDate(applicationOpenDate),
                formatDate(applicationCloseDate),
                formatFlats(),
                visibility ? "ON" : "OFF",
                officers.size());
    }

    private String formatDate(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private String formatFlats() {
        if (flats == null || flats.isEmpty())
            return "None";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<FlatType, Integer> entry : flats.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units, ");
        }
        return sb.substring(0, sb.length() - 2);
    }

}
