package com.example.app.models;

import jakarta.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Applicant applicant;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application() { }

    public Application(Applicant applicant, Project project, ApplicationStatus status) {
        this.applicant = applicant;
        this.project = project;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant applicant) { this.applicant = applicant; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}

// enum ApplicationStatus {
//     FOUND,
//     BOOKED,
//     SUCCESSFUL,
//     UNSUCCESSFUL
// }

