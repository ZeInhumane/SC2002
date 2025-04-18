package com.example.app.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Calendar;

import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.example.app.models.FlatType;
import com.example.app.models.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.repository.ProjectRepository;



public class ProjectService {

    private static ProjectRepository projectRepository = new ProjectRepository();

    public ProjectService() {
    
    }


    // Get projects meant for manager
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    public Collection<Project> findByManagerId(int managerId) {
        return projectRepository.findByManagerId(managerId);
    }


    // Get projects meant for applicant and officer
    public Collection<Project> findByMaritalStatusAndVisibility(MaritalStatus userStatus, boolean visibility) {
        System.out.println("pog");
        System.out.println(userStatus);
        System.out.println(visibility);
        return projectRepository.findByMaritalStatusAndVisibility(userStatus, visibility);
    }

    
    // Check if the person can eligible for a flat type 
    // Can be used to display and alter the application button if no option is there 
    // User should not choose what flat to apply
    public List<FlatType> getEligibleFlatTypes(MaritalStatus userStatus, int userAge) {
        List<FlatType> eligible = new ArrayList<>();

        Project project = projectRepository.findById(userAge);


        for (FlatType type : project.getFlats().keySet()) {
            if (project.hasFlatLeft(type)) {
                if (userStatus == MaritalStatus.SINGLE && userAge >= 35 && type == FlatType._2ROOM) {
                    eligible.add(type);
                } else if (userStatus == MaritalStatus.MARRIED && userAge >= 21 &&
                        (type == FlatType._2ROOM || type == FlatType._3ROOM)) {
                    eligible.add(type);
                }
            }
        }

        return eligible;
    }

    public Project findById(int projectId) {
        return projectRepository.findById(projectId);
    }


    // For officer to decremeent flat count if applicant book
    public void decrementFlatCount(int projectId, FlatType flatType) {
        Project project = findById(projectId);
        project.decrementFlatCount(flatType);
    }

    public boolean isOfficerFor(int userId, int projectId) {
        Project project = projectRepository.findById(projectId);
        return project.getOfficers().contains(userId);
    }

    public boolean isProjectStillApplying(int projectId) {
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


    // For manager to add officer to flat project
    public void addOfficer(int userId, int projectId) {
        Project project = findById(projectId);
        project.addOfficer(userId);
    }

    // For manager to remove officer to flat project
    public void removeeOfficer(int userId, int projectId) {
        Project project = findById(projectId);
        project.removeOfficer(userId);
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
