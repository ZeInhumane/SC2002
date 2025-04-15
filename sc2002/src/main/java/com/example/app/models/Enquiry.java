package com.example.app.models;

import jakarta.persistence.*;

@Entity
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String response;

    @ManyToOne
    private User postedBy;

    public Enquiry() { }

    public Enquiry(String question, String response, User postedBy) {
        this.question = question;
        this.response = response;
        this.postedBy = postedBy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
}

