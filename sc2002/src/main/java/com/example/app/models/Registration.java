package com.example.app.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A registration links a user and a project
    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredOn;

    public Registration() { }

    public Registration(User user, Project project, Date registeredOn) {
        this.user = user;
        this.project = project;
        this.registeredOn = registeredOn;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Date getRegisteredOn() { return registeredOn; }
    public void setRegisteredOn(Date registeredOn) { this.registeredOn = registeredOn; }
}
