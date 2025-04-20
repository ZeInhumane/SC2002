package com.example.app.service;

import com.example.app.models.Enquiry;
import com.example.app.models.Project;

import java.util.List;

public interface AdminService {
    public List<Project> viewHandledProjects();

    public List<Enquiry> viewHandledEnquiries();
}
