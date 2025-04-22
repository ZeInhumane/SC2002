package com.example.app.cli;

import com.example.app.cli.utils.*;
import com.example.app.models.Applicant;
import com.example.app.control.ApplicantControl;
import com.example.app.service.impl.ApplicantServiceImpl;
import com.example.app.RuntimeData;
import com.example.app.models.*;

import java.io.IOException;
import java.util.List;

public class ApplicantUI implements BaseUI {
    private ApplicantControl applicantControl;

    ApplicantUI() {
        this.applicantControl = new ApplicantControl(new ApplicantServiceImpl());
    }
    
    @Override
    public void printMenu() {
        System.out.println("1. View Projects");
        System.out.println("2. View Applied Projects");
        System.out.println("3. View Enquiries");
        System.out.println("4. Back");
    }

    @Override
    public void run() {
        while (true) {
            printMenu();
            int choice = Readers.readIntRange(1, 4);
            Helper.wipeScreen();
            
            switch (choice) {
                case 1:
                    new ApplicantProjectUI(applicantControl).run();
                    break;
                case 2:
                    // viewAppliedProjects();
                    break;
                case 3:
                    // viewEnquiries();
                    break;
                case 4:
                    return; // Go back to the previous menu
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
    }
}
