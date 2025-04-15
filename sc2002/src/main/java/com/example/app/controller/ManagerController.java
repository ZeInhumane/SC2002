package com.example.app.controller;


import com.example.app.models.Manager;
import com.example.app.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
    
    @Autowired
    private ManagerService managerService;
    
    @GetMapping
    public List<Manager> getAllManagers() {
       return managerService.findAllManagers();
    }
    
    @GetMapping("/{id}")
    public Manager getManager(@PathVariable Long id) {
       Optional<Manager> managerOpt = managerService.findById(id);
       return managerOpt.orElse(null);
    }
    
    @PostMapping
    public Manager createManager(@RequestBody Manager manager) {
       return managerService.createManager(manager);
    }
    
    @DeleteMapping("/{id}")
    public void deleteManager(@PathVariable Long id) {
       managerService.deleteManager(id);
    }
}
