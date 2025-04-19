package com.example.app.cli;

import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.enums.FlatType;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;
import com.example.app.utils.Console;

import java.util.Collection;
import java.util.List;

public class ApplicantCLI {
    private final ApplicantService appService;

    public ApplicantCLI(ApplicantService appService) {
        this.appService = appService;
    }

    public void run() {
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
    protected void viewProjects() {
        Collection<Project> projects = appService.viewProjects();
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
    protected void applyForProject() {
        
        // Check if user already has an application that is pending/successful/booked
        if (!appService.isAbleToApply()) {
            System.out.println("You already have an active or successful application.");
            return;
        }

        // Get the projects         
        viewProjects();

        // Enter the project you want to apply for 
        int projectId = Console.readInt("Enter project ID to apply: ");

        List<FlatType> eligibleTypes;
        try {
            eligibleTypes = appService.getEligibleFlatTypesForProject(projectId);
        } catch (Exception e) {
            System.out.println(" " + e.getMessage());
            return;
        }

        // If you are not an officer for (just checks if id is in user)
        if (appService.isOfficerFor(projectId)) {
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
            appService.applyForProject(projectId, selectedType);
            System.out.println(" Application submitted with preferred flat type: " + selectedType);
        } catch (Exception e) {
            System.out.println(" Failed to apply: " + e.getMessage());
        }
    }


    // View applicants current application
    protected void viewCurrentApplication() {
        Application app = appService.viewCurrentApplication();
        if (app == null) {
            System.out.println("No application found.");
        } else {
            System.out.println(app);
        }
    }

    // Submit enquiry
    protected void submitEnquiry() {
        Collection<Project> projects = appService.viewProjects();
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
            int enquiryId = appService.submitEnquiry(question, projectId);
            System.out.println("Enquiry submitted (ID: " + enquiryId + ")");
        } catch (Exception e) {
            System.out.println("Failed to submit enquiry: " + e.getMessage());
        }
    }

    // View applicant enquiruies
    protected void viewMyEnquiries() {
        List<Enquiry> enquiries = appService.getAllPastEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    // Edit applicant enquiries
    protected void editEnquiry() {
        List<Enquiry> enquiries = appService.getAllPastEnquiries();
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
            appService.updateEnquiry(id, updated);
            System.out.println("Enquiry updated.");
        } catch (Exception e) {
            System.out.println("Could not update: " + e.getMessage());
        }
    }


    // Delete Enquiry
    protected void deleteEnquiry() {
        List<Enquiry> enquiries = appService.getAllPastEnquiries();
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
            appService.deleteEnquiry(id);
            System.out.println("Enquiry deleted.");
        } catch (Exception e) {
            System.out.println("Could not delete: " + e.getMessage());
        }
    }

    // reuse existing display method
    protected void handleEnquiry() {
        viewMyEnquiries();

        if (appService.getAllPastEnquiries().isEmpty())
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
