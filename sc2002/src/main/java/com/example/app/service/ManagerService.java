package com.example.app.service;

public class ManagerService implements AdminService{

    public void viewHandledProjects(){
        viewHandledEnquiries();
    }

    public void viewHandledEnquiries(){
        viewHandledProjects();
    }

}


