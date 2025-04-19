package com.example.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.exceptions.OfficerAlreadyInsideException;
import com.example.app.exceptions.OfficerLimitExceededException;

public class Project implements BaseEntity {

    private Integer id;
    private String projectName;
    private Date applicationOpenDate;
    private Date applicationCloseDate;
    private String neighborhood;
    private Integer managerId;
    private MaritalStatus group;
    private Boolean visibility;
    private Map<FlatType, Integer> flatCount;


    public Project() {
    }

    public Project(Integer id, String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood,
            Integer managerId, MaritalStatus group, Boolean visibility, Map<FlatType, Integer> flatCount) {
        this();
        this.projectName = projectName;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.neighborhood = neighborhood;
        this.managerId = managerId;
        this.group = group;
        this.visibility = visibility;
        this.flatCount = flatCount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
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

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void decrementFlatCount(FlatType flatType) {
        if (!flatCount.containsKey(flatType)) {
            throw new IllegalArgumentException("Flat type " + flatType + " is not available in this project.");
        }

        int currentCount = flatCount.get(flatType);
        if (currentCount <= 0) {
            throw new IllegalStateException("No flats of type " + flatType + " are left to allocate.");
        }

        flatCount.put(flatType, currentCount - 1);
    }

    public boolean hasFlatLeft(FlatType flatType) {
        return flatCount.getOrDefault(flatType, 0) > 0;
    }

    public Map<FlatType, Integer> getFlats() {
        return flatCount;
    }

    public void setFlats(Map<FlatType, Integer> flatCount) {
        this.flatCount = flatCount;
    }

    @Override
    public String toString() {
        return String.format("""
                ----------------------------------------
                [Project Id: %s]
                ~~~~~~~~~~~~~~~~~
                Name: %s (Application Period: %s to %s)
                Neighborhood: %s  For Group: %s People
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
                group,
                formatFlats(),
                visibility ? "ON" : "OFF", managerId);
    }

    private String formatDate(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private String formatFlats() {
        if (flatCount == null || flatCount.isEmpty())
            return "None";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<FlatType, Integer> entry : flatCount.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units, ");
        }
        return sb.substring(0, sb.length() - 2);
    }

}
