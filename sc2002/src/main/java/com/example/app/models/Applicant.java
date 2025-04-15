package com.example.app.models;

import jakarta.persistence.Entity;

@Entity
public class Applicant extends User {

    private String resumeLink;

    public Applicant() { }

    public Applicant(String username, String password, String email, Role role, String resumeLink) {
       super(username, password, email, role);
       this.resumeLink = resumeLink;
    }

    public String getResumeLink() { return resumeLink; }
    public void setResumeLink(String resumeLink) { this.resumeLink = resumeLink; }
}
