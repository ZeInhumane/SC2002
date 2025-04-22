package com.example.app.models;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

public class User implements BaseEntity{

    private Integer id;
    private String name;
    private String password;
    private String email;
    private Role role;
    private String nric;
    private Integer age;
    private MaritalStatus maritalStatus;

    public User() {
    }

    public User(Integer id, String name, String password, String email, Role role, String nric, Integer age, MaritalStatus maritalStatus) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("""
                [User ID: %d]
                Name: %s
                NRIC: %s
                Age: %d
                Marital Status: %s
                Email: %s
                """,
                id,
                name,
                nric,
                age,
                maritalStatus,
                email
        );
    }
}
