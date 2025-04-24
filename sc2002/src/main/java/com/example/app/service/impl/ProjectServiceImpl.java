package com.example.app.service.impl;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.repository.ProjectRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.ProjectService;

import java.io.IOException;
import java.util.*;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository = RepositoryDependency.getProjectRepository();

    @Override
    public Project findById(int id) throws IOException {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findByManagerId(int managerId) throws IOException {
        return projectRepository.findByManagerId(managerId);
    }

    @Override
    public Project findByManagerIdAndIsActive(int managerId) throws IOException {
        return projectRepository.findByManagerIdAndOpenDateGreaterThanAndCloseDateLessThan(managerId, new Date());
    }

    @Override
    public List<Project> findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(boolean visibility, Date date) throws IOException {
        return projectRepository.findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(visibility, date);
    }

    // Get projects meant for manager
    @Override
    public List<Project> findAll() throws IOException {
        return projectRepository.findAll();
    }

    // Get projects meant for applicant
    @Override
    public List<Project> getPublicProjects(MaritalStatus userStatus, boolean visibility, Date date) throws IOException {
        return projectRepository.findByMaritalStatusAndVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(userStatus,
                visibility, date);
    }

    // Check if project can be applied for date
    @Override
    public boolean isActive(int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            return false;
        }

        Date now = new Date();
        Date openDate = project.getApplicationOpenDate();
        Date closeDate = project.getApplicationCloseDate();
        // System.out.println(now);
        // System.out.println(now.compareTo(openDate) >= 0 && now.compareTo(closeDate) <= 0);
        return now.compareTo(openDate) >= 0 && now.compareTo(closeDate) <= 0;
    }

    // Create Project For managers
    @Override
    public Project createProject(String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, int managerId, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException {
        Project project = new Project(null, projectName, stripTime(applicationOpenDate),
                stripTime(applicationCloseDate), neighborhood, managerId, visibility, officerLimit, officers, groups, flats);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(int projectId, String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, int managerId, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        project.setProjectName(projectName);
        project.setApplicationOpenDate(stripTime(applicationOpenDate));
        project.setApplicationCloseDate(stripTime(applicationCloseDate));
        project.setNeighborhood(neighborhood);
        project.setManagerId(managerId);
        project.setVisibility(visibility);
        project.setGroups(groups);
        project.setFlats(flats);

        return projectRepository.save(project);
    }

    @Override
    public Project addOfficer(int projectId, int officerId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        Set<Integer> officers = project.getOfficers();
        if (officers == null) {
            officers = new HashSet<>();
        }

        if (officers.size() >= project.getOfficerLimit()) {
            throw new IllegalArgumentException("Officer limit reached for this project.");
        }
        officers.add(officerId);
        project.setOfficers(officers);

        return projectRepository.save(project);
    }

    // For officer to decremeent flat count if applicant book
    @Override
    public Project decrementFlatCount(int projectId, FlatType flatType) throws IOException {
        Project project = projectRepository.findById(projectId);
        project.decrementFlatCount(flatType);
        return projectRepository.save(project);
    }

    // Remove Project for managers
    @Override
    public void deleteProject(int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        projectRepository.deleteById(projectId);
    }

    @Override
    public Project save(Project project) throws IOException {
        return projectRepository.save(project);
    }

    private Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Filtered View for project
}
