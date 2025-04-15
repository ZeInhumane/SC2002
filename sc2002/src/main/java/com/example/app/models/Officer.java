package com.example.app.models;

import jakarta.persistence.Entity;

@Entity
public class Officer extends User {

    private String rank;

    public Officer() { }

    public Officer(String username, String password, String email, Role role, String rank) {
        super(username, password, email, role);
        this.rank = rank;
    }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
}

