package com.example.app.controller;

import com.example.app.models.User;
import com.example.app.service.UserService;
import com.example.app.utils.InputReader;

public class UserController {

    private UserService userService;

    public static void controller() {

    }

    public void login(){
        System.out.println("Enter your NIRC");
        String nirc = InputReader.readString();
        System.out.println("Enter your password");
        String password = InputReader.readString();
        User user = userService.login(nirc, password);
        if(user == null){
            System.out.println("Invalid NIRC or password");
            return;
        }
        System.out.println("Welcome " + user.getNirc());
        switch (user.getRole()){
            case APPLICANT:
                ApplicantController.controller();
                break;
            case OFFICER:
                OfficerController.controller();
                break;
            case MANAGER:
                ManagerController.controller();
                break;
        }

    }
}
