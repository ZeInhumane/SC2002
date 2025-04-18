package com.example.app.cli;

import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.service.OfficerService;
import com.example.app.utils.Console;

import java.util.List;

public class OfficerCLI extends ApplicantCLI {
    private final OfficerService officerService;

    public OfficerCLI(OfficerService officerService) {
        super(officerService); // reuse ApplicantCLI logic
        this.officerService = officerService;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("\n=== Officer Menu (includes Applicant features) ===");
            System.out.println("1) View Available Projects");
            System.out.println("2) Apply for Project");
            System.out.println("3) View Current Application");
            System.out.println("4) Submit Enquiry");
            System.out.println("5) View My Enquiries");
            System.out.println("6) Edit/Delete My Enquiries");
            System.out.println("7) View Project Enquiries (Officer)");
            System.out.println("8) View Enquiry by ID (Officer)");
            System.out.println("9) Reply to Enquiry (Officer)");
            System.out.println("10) Register as Officer for Project");
            System.out.println("11) View My Officer Registration");
            System.out.println("0) Logout");

            int choice = Console.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applyForProject();
                case 3 -> viewCurrentApplication();
                case 4 -> submitEnquiry();
                case 5 -> viewMyEnquiries();
                case 6 -> handleEnquiry();
                case 7 -> viewProjectEnquiries();
                case 8 -> viewEnquiryById();
                case 9 -> replyToEnquiry();
                case 10 -> registerAsOfficer();
                case 11 -> viewMyRegistration();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewProjectEnquiries() {
        try {
            List<Enquiry> enquiries = officerService.getProjectEnquiries();
            if (enquiries.isEmpty()) {
                System.out.println("📭 No enquiries submitted for your project.");
                return;
            }
            System.out.println("=== Enquiries for Your Project ===");
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void viewEnquiryById() {
        viewProjectEnquiries();
        int id = Console.readInt("Enter Enquiry ID to view: ");
        Enquiry e = officerService.getEnquiry(id);
        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("❌ Enquiry not found or not part of your project.");
        }
    }

    private void replyToEnquiry() {
        viewProjectEnquiries();
        int id = Console.readInt("Enter Enquiry ID to reply: ");
        Enquiry e = null;

        try {
            e = officerService.getEnquiry(id);
        } catch (IllegalArgumentException ex) {
            System.out.println("❌ Enquiry ID not found.");
            return;
        }

        if (e == null) {
            System.out.println("❌ Enquiry not found.");
            return;
        }

        System.out.println("Question: " + e.getQuestion());
        System.out.println("Current Reply: " + (e.getResponse() != null ? e.getResponse() : "(none)"));
        String reply = Console.readLine("Enter reply: ");

        try {
            officerService.replyEnquiry(id, reply);
            System.out.println("Reply submitted.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void registerAsOfficer() {
        List<Project> registerableProjects = officerService.getRegisterableProjects();

        if (registerableProjects.isEmpty()) {
            System.out.println("No projects available for officer registration.");
            return;
        }

        System.out.println("=== Projects You Can Register For ===");
        for (Project p : registerableProjects) {
            System.out.println(p); // uses toString()
        }

        int projectId = Console.readInt("Enter Project ID to register for: ");
        if (officerService.isNotRegisterableAsOfficer(projectId)) {
            System.out.println(
                    "You cannot register for this project. You're either an applicant or already handling another.");
            return;
        }

        try {
            officerService.registerAsOfficer(projectId);
            System.out.println("Registered as officer for project ID: " + projectId);
        } catch (Exception e) {
            System.out.println("Failed to register: " + e.getMessage());
        }
    }

    private void viewMyRegistration() {
        try {
            Registration r = officerService.viewCurrentRegistration();
            System.out.println("=== Your Officer Registration ===");
            System.out.println("Project ID: " + r.getProjectId());
            System.out.println("Status: " + r.getStatus());
        } catch (Exception e) {
            System.out.println("❌ You are not registered for any project.");
        }
    }
}
