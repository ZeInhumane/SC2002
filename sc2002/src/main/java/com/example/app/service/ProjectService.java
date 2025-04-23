package com.example.app.service;

import java.io.IOException;
import java.util.*;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;

public interface ProjectService {

    // Find project by id
    // Also works with the only project that the officer is managing
    Project findById(int id) throws IOException;

    // Get projects meant for manager
    List<Project> findByManagerId(int managerId) throws IOException;

    Project findByManagerIdAndIsActive(int managerId) throws IOException;

    List<Project> findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(boolean visibility, Date date) throws IOException;

    // Get all projects for manager view
    List<Project> findAll() throws IOException;

    // Get projects meant for applicant
    List<Project> getPublicProjects(MaritalStatus userStatus, boolean visibility, Date date) throws IOException;

    // Check if a project is active
    // For applicant to apply
    // For officer to register
    // Still, officer can decrement flat count even if project is not active
    boolean isActive(int projectId) throws IOException;

    // for manager to create a new project
    Project createProject(String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood,
            int managerId, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups, Map<FlatType, Integer> flats)
            throws IOException;

    // for manager to update project
    Project updateProject(int projectId, String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, int managerId, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException;

    Project addOfficer(int projectId, int officerId) throws IOException;

    Project decrementFlatCount(int projectId, FlatType flatType) throws IOException;

    // for manage to delete project
    void deleteProject(int projectId) throws IOException;

    Project save(Project project) throws IOException;
}
