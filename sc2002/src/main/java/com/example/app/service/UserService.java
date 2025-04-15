package com.example.app.service;

import com.example.app.models.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAllUsers() {
       return userRepository.findAll();
    }
    
    public User createUser(User user) {
       return userRepository.save(user);
    }
    
    public Optional<User> findById(Long id) {
       return userRepository.findById(id);
    }
    
    public void deleteUser(Long id) {
       userRepository.deleteById(id);
    }
}
