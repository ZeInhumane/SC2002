package com.example.app.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Calendar;

import java.util.List;
import java.util.Map;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.repository.ProjectRepository;



public class ProjectService {

    private static ProjectRepository projectRepository = new ProjectRepository();

    public ProjectService() {
    
    }


    public Project findById(int id) throws IOException {
        return projectRepository.findById(id);
    }

    // Get projects meant for manager
    public List<Project> findAll() throws IOException {
        return projectRepository.findAll();
    }

    public List<Project> findByManagerId(int managerId) throws IOException {
        return projectRepository.findByManagerId(managerId);
    }


    // Get projects meant for applicant and officer
    public List<Project> findByMaritalStatusAndVisibility(MaritalStatus userStatus, boolean visibility) throws IOException {
        return projectRepository.findByMaritalStatusAndVisibility(userStatus, visibility);
    }


    // Get projects meant for applicant and officer without constraint of marital status
    public List<Project> findByVisibility(boolean visibility) throws Exception {
        return projectRepository.findByVisibility(visibility);
    }



    // For officer to decremeent flat count if applicant book
    public void decrementFlatCount(int projectId, FlatType flatType) throws IOException {
        Project project = projectRepository.findById(projectId);
        project.decrementFlatCount(flatType);
    }

    public boolean isOfficerFor(int userId, int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        return project.getOfficers().contains(userId);
    }


    // Check if project can be applied for date
    public boolean isProjectStillApplying(int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            return false;
        }

        Date now = new Date();
        Date openDate = project.getApplicationOpenDate();
        Date closeDate = project.getApplicationCloseDate();
        System.out.println(now);
        System.out.println(now.compareTo(openDate) >= 0 && now.compareTo(closeDate) <= 0);
        return now.compareTo(openDate) >= 0 && now.compareTo(closeDate) <= 0;
    }



    // Create Project For managers 
    public int createProject(
        String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood, MaritalStatus group, Map<FlatType, Integer> flats, int managerId
    ) {
        Project project = new Project(projectName, stripTime(applicationOpenDate), stripTime(applicationCloseDate), neighborhood,  managerId,  group,  true,  flats);
        return projectRepository.save(project).getId();
    }

    // Allow managers to edit project
    // Can set visibility
    public void editProject(int projectId, String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood, MaritalStatus group, Map<FlatType, Integer> flats, boolean visibility) {
        Project project = projectRepository.findById(projectId);

        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        // Update editable fields
        project.setProjectName(projectName);
        project.setApplicationOpenDate(applicationOpenDate);
        project.setApplicationCloseDate(applicationCloseDate);
        project.setNeighborhood(neighborhood);
        project.setGroup(group);
        project.setFlats(flats);  
        project.setVisibility(visibility);
    }


    // Remove Project for managers
    public void deleteProject(int projectId) {
    Project project = projectRepository.findById(projectId);
    if (project == null) {
        throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
    }

        projectRepository.deleteById(projectId);
    }

    public Date stripTime(Date date) {
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
