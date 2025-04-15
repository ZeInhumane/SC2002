package com.example.app.service;


import com.example.app.models.Manager;
import com.example.app.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    
    @Autowired
    private ManagerRepository managerRepository;
    
    public List<Manager> findAllManagers() {
       return managerRepository.findAll();
    }
    
    public Manager createManager(Manager manager) {
       return managerRepository.save(manager);
    }
    
    public Optional<Manager> findById(Long id) {
       return managerRepository.findById(id);
    }
    
    public void deleteManager(Long id) {
       managerRepository.deleteById(id);
    }
}
