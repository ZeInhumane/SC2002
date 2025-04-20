package com.example.app.models;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    public Manager() { }

    public Manager(Integer id, String username, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(id, username, password, email, role, nric, age, maritalStatus);
    }

}
