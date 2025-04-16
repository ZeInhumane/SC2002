package com.example.app.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.app.models.FlatType;
import com.example.app.models.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.models.User;
import com.example.app.repository.ProjectRepository;

public class ProjectService extends GeneralService {

    private ProjectRepository projectRepository = new ProjectRepository();

    public ProjectService(User user) {
        super(user); 
    }

    // Get all projects based on user role
    public Collection<Project> getAllProjects() {
        switch (user.getRole()) {
            case MANAGER:
                return projectRepository.findAll();

            case APPLICANT:
            case OFFICER:
                return projectRepository.findByMaritalStatusAndVisibility(user.getMaritalStatus(), true);

            default:
                throw new IllegalArgumentException("Unhandled role: " + user.getRole());
        }
    }  
    
    // Check if the person can eligible for a flat type 
    // Can be used to display and alter the application button if no option is there 
    // User should not choose what flat to apply
    public List<FlatType> getEligibleFlatTypes() {
        List<FlatType> eligible = new ArrayList<>();
        MaritalStatus status = user.getMaritalStatus();
        int age = user.getAge();
        
        Project project = projectRepository.findById(age);


        for (FlatType type : project.getFlats().keySet()) {
            if (project.hasFlatLeft(type)) {
                if (status == MaritalStatus.SINGLE && age >= 35 && type == FlatType._2ROOM) {
                    eligible.add(type);
                } else if (status == MaritalStatus.MARRIED && age >= 21 &&
                        (type == FlatType._2ROOM || type == FlatType._3ROOM)) {
                    eligible.add(type);
                }
            }
        }

        return eligible;
    }


    // For officer to decremeent flat count if applicant book
    public void decrementFlatCount(int projectId, FlatType flatType) {
        Project project = projectRepository.findById(projectId);
        project.decrementFlatCount(flatType);
    }

}
