package com.example.app.controller;

import com.example.app.models.Officer;
import com.example.app.service.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/officers")
public class OfficerController {
    
    @Autowired
    private OfficerService officerService;
    
    @GetMapping
    public List<Officer> getAllOfficers() {
       return officerService.findAllOfficers();
    }
    
    @GetMapping("/{id}")
    public Officer getOfficer(@PathVariable Long id) {
       Optional<Officer> officerOpt = officerService.findById(id);
       return officerOpt.orElse(null);
    }
    
    @PostMapping
    public Officer createOfficer(@RequestBody Officer officer) {
       return officerService.createOfficer(officer);
    }
    
    @DeleteMapping("/{id}")
    public void deleteOfficer(@PathVariable Long id) {
       officerService.deleteOfficer(id);
    }
}
