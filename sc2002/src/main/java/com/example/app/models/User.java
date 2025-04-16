package com.example.app.models;

public class User {

    private static long idCounter = 1;
    private long id;

    private String nric;
    private String name;
    private String password;
    private String email;
    private MaritalStatus maritalStatus;
    private int age;
    private Role role;

    public User() {
        this.id = idCounter++;
    }

    public User(String name, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        this(); // auto-generate ID
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    // Getters and Setters

    public long getId() {
        return id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
