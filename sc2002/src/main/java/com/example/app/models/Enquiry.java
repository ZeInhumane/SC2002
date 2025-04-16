package com.example.app.models;

public class Enquiry {

    private static long counter = 1; // for manual ID generation

    private Long id;
    private String question;
    private String response;

    private Project project;
    private User enquirer;
    private User replier;

    public Enquiry() {
        this.id = counter++;
    }

    public Enquiry(String question, String response, Project project, User enquirer, User replier) {
        this.id = counter++;
        this.question = question;
        this.response = response;
        this.project = project;
        this.enquirer = enquirer;
        this.replier = replier;
    }

    public Long getId() { return id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public User getEnquirer() { return enquirer; }
    public void setEnquirer(User enquirer) { this.enquirer = enquirer; }

    public User getReplier() { return replier; }
    public void setReplier(User replier) { this.replier = replier; }
}
