package com.example.app.cli;

import com.example.app.RuntimeData;
import com.example.app.enums.Role;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.enums.FlatType;
import com.example.app.models.Officer;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;
import com.example.app.utils.Console;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ApplicantCLI {
    private final ApplicantService appService;

    public ApplicantCLI(ApplicantService appService) {
        this.appService = appService;
    }

    public void run() throws IOException {
        while (true) {
            System.out.println("\n=== Applicant Menu ===");
            System.out.println("1) View Available Projects");
            System.out.println("2) Apply for Project");
            System.out.println("3) View Current Application");
            System.out.println("4) Submit Enquiry");
            System.out.println("5) View My Enquiries");
            System.out.println("0) Logout");

            int choice = Console.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewProjects(); // Done
                case 2 -> applyForProject(); // Done
                case 3 -> viewCurrentApplication();
                case 4 -> submitEnquiry();
                case 5 -> handleEnquiry();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // View projects eligible to applicant based on marital status and visibility
    protected void viewProjects() throws IOException {
        Collection<Project> projects = appService.viewPublicProjects(RuntimeData.getCurrentApplicant());
        if (projects.isEmpty()) {
            System.out.println("No available projects at the moment.");
            return;
        }

        // Load projects out
        System.out.println("=== Available Projects ===");
        for (Project p : projects) {
            System.out.println(p);
        }
    }

    // Apply for Project
    protected void applyForProject() throws IOException {
        
        // Check if user already has an application that is pending/successful/booked
        if (!appService.isAbleToApply(RuntimeData.getCurrentApplicant())) {
            System.out.println("You already have an active or successful application.");
            return;
        }

        // Get the projects         
        viewProjects();

        // Enter the project you want to apply for 
        int projectId = Console.readInt("Enter project ID to apply: ");

        List<FlatType> eligibleTypes;
        try {
            eligibleTypes = appService.getEligibleFlatTypesForProject(RuntimeData.getCurrentApplicant(), projectId);
        } catch (Exception e) {
            System.out.println(" " + e.getMessage());
            return;
        }

        // If you are not an officer for (just checks if id is in user)
        if (RuntimeData.getCurrentApplicant().getRole() == Role.OFFICER && RuntimeData.getCurrentOfficer().getRegisteredProject() == projectId) {
            System.out.println("You are an officer for this project and hence cannot apply");
            return;
        }


        // Check of there are any flats that are eligible
        if (eligibleTypes.isEmpty()) {
            System.out.println("No eligible flat types available for your profile in this project.");
            return;
        }

        // If there is choose a flat
        System.out.println("Eligible Flat Types:");
        for (int i = 0; i < eligibleTypes.size(); i++) {
            System.out.println((i + 1) + ") " + eligibleTypes.get(i));
        }

        int choice = Console.readInt("Select a flat type (by number): ");
        if (choice < 1 || choice > eligibleTypes.size()) {
            System.out.println("Invalid flat type selection.");
            return;
        }

        FlatType selectedType = eligibleTypes.get(choice - 1);

        try {
            appService.applyForProject(RuntimeData.getCurrentApplicant(), projectId, selectedType);
            System.out.println(" Application submitted with preferred flat type: " + selectedType);
        } catch (Exception e) {
            System.out.println(" Failed to apply: " + e.getMessage());
        }
    }


    // View applicants current application
    protected void viewCurrentApplication() throws IOException {
        Application app = appService.viewCurrentApplication(RuntimeData.getCurrentApplicant());
        if (app == null) {
            System.out.println("No application found.");
        } else {
            System.out.println(app);
        }
    }

    // Submit enquiry
    protected void submitEnquiry() throws IOException {
        Collection<Project> projects = appService.viewPublicProjects(RuntimeData.getCurrentApplicant());
        if (projects.isEmpty()) {
            System.out.println("No projects available to submit enquiry.");
            return;
        }

        for (Project p : projects) {
            System.out.println("ID " + p.getId() + " > " + p.getProjectName());
        }

        int projectId = Console.readInt("Enter Project ID for enquiry: ");
        String question = Console.readLine("Enter your enquiry: ");
        try {
            Enquiry enquiryId = appService.submitEnquiry(RuntimeData.getCurrentApplicant(), question, projectId);
            System.out.println("Enquiry submitted (ID: " + enquiryId + ")");
        } catch (Exception e) {
            System.out.println("Failed to submit enquiry: " + e.getMessage());
        }
    }

    // View applicant enquiruies
    protected void viewMyEnquiries() throws IOException {
        List<Enquiry> enquiries = appService.getAllPastEnquiries(RuntimeData.getCurrentApplicant());
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    // Edit applicant enquiries
    protected void editEnquiry() throws IOException {
        List<Enquiry> enquiries = appService.getAllPastEnquiries(RuntimeData.getCurrentApplicant());
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (Enquiry e : enquiries) {
            System.out.println(e); // uses toString()
        }

        int id = Console.readInt("Enter Enquiry ID to edit: ");
        String updated = Console.readLine("Enter new question: ");

        try {
            appService.updateEnquiry(RuntimeData.getCurrentApplicant(), id, updated);
            System.out.println("Enquiry updated.");
        } catch (Exception e) {
            System.out.println("Could not update: " + e.getMessage());
        }
    }


    // Delete Enquiry
    protected void deleteEnquiry() throws IOException {
        List<Enquiry> enquiries = appService.getAllPastEnquiries(RuntimeData.getCurrentApplicant());
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to delete.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (Enquiry e : enquiries) {
            System.out.println(e); // uses toString()
        }

        int id = Console.readInt("Enter Enquiry ID to delete: ");
        try {
            appService.deleteEnquiry(RuntimeData.getCurrentApplicant(), id);
            System.out.println("Enquiry deleted.");
        } catch (Exception e) {
            System.out.println("Could not delete: " + e.getMessage());
        }
    }

    // reuse existing display method
    protected void handleEnquiry() throws IOException {
        viewMyEnquiries();

        if (appService.getAllPastEnquiries(RuntimeData.getCurrentApplicant()).isEmpty())
            return;

        System.out.println("1) Edit Enquiry");
        System.out.println("2) Delete Enquiry");
        System.out.println("0) Back");

        int choice = Console.readInt("Choose an option: ");
        switch (choice) {
            case 1 -> editEnquiry();
            case 2 -> deleteEnquiry();
            case 0 -> System.out.println("Returning to menu...");
            default -> System.out.println("Invalid choice.");
        }
    }

}
