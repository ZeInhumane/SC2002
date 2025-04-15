package com.example.app.service;

import com.example.app.models.Project;
import com.example.app.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    public List<Project> findAllProjects() {
       return projectRepository.findAll();
    }
    
    public Project createProject(Project project) {
       return projectRepository.save(project);
    }
    
    public Optional<Project> findById(Long id) {
       return projectRepository.findById(id);
    }
    
    public void deleteProject(Long id) {
       projectRepository.deleteById(id);
    }
}

