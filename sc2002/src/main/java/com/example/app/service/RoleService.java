package com.example.app.service;

import com.example.app.models.Role;
import com.example.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public List<Role> findAllRoles() {
       return roleRepository.findAll();
    }
    
    public Role createRole(Role role) {
       return roleRepository.save(role);
    }
    
    public Optional<Role> findById(Long id) {
       return roleRepository.findById(id);
    }
    
    public void deleteRole(Long id) {
       roleRepository.deleteById(id);
    }
}

