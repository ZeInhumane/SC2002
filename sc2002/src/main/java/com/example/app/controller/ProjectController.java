package com.example.app.controller;

import com.example.app.models.Project;
import com.example.app.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public List<Project> getAllProjects() {
       return projectService.findAllProjects();
    }
    
    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
       Optional<Project> projectOpt = projectService.findById(id);
       return projectOpt.orElse(null);
    }
    
    @PostMapping
    public Project createProject(@RequestBody Project project) {
       return projectService.createProject(project);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
       projectService.deleteProject(id);
    }
}
