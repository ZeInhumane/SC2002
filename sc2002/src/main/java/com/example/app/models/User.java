package com.example.app.models;

public class User {
    private String nirc;
    private String password;
    private Role role;

    public String getNirc() {
        return this.nirc;
    }

    public void setNirc(String nirc) {
        this.nirc = nirc;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
