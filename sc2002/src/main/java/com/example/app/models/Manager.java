package com.example.app.models;

import jakarta.persistence.Entity;

@Entity
public class Manager extends User {

    private String departmentName;

    public Manager() { }

    public Manager(String username, String password, String email, Role role, String departmentName) {
       super(username, password, email, role);
       this.departmentName = departmentName;
    }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
