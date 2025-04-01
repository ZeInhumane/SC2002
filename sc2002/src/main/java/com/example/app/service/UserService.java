package com.example.app.service;

import com.example.app.models.User;
import com.example.app.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;
    public User login(String nirc, String password){

        User user = userRepository.findByNirc(nirc);
        if(user == null){
            return null;
        }
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }
}
