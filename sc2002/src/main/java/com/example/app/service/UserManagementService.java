package com.example.app.service;

import com.example.app.models.FlatType;
import com.example.app.models.FlatTypeAssignable;
import com.example.app.models.ProjectAssignable;
import com.example.app.models.User;
import com.example.app.repository.UserRepository;

public class UserManagementService {
    private UserRepository userRepository = new UserRepository();
    

    // Let Officer and Manager find the user
    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    // Let Officer and Manager edit user flat type when booking it
    public void editFlatType(int userId, FlatType flatType) {
        User user = findById(userId);

        if (user instanceof FlatTypeAssignable assignable) {
            assignable.setFlatType(flatType);
        }

    }   


    // Update officer portfolio on which project he is handling
    public void assignProjectToOfficer(int userId, int projectId) {
        User user = userRepository.findById(userId);

        if (user instanceof ProjectAssignable assignable) {
            assignable.setRegisteredProject(projectId);
        } else {
            throw new IllegalArgumentException("User is not assignable to projects.");
        }
    }


}
