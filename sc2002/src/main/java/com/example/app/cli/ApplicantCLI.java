package com.example.app.cli;

import com.example.app.models.Application;
import com.example.app.models.Enquiry;
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
            System.out.println("6) Edit Enquiry");
            System.out.println("7) Delete Enquiry");
            System.out.println("0) Logout");

            int choice = Console.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewProjects(); // Done
                case 2 -> applyForProject(); // Done
                case 3 -> viewCurrentApplication();
                case 4 -> submitEnquiry();
                case 5 -> viewMyEnquiries();
                case 6 -> editEnquiry();
                case 7 -> deleteEnquiry();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewProjects() {
        Collection<Project> projects = appService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("No available projects at the moment.");
        } else {
            for (Project p : projects) {
                System.out.println(p);
            }
        }
    }

    private void applyForProject() {
        if (!appService.isAbleToApply()) {
            System.out.println("⚠️ You already have an active or successful application.");
            return;
        }

        Collection<Project> projects = appService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("📭 No available projects to apply for at the moment.");
            return;
        }

        System.out.println("=== Available Projects ===");
        for (Project p : projects) {
            System.out.println(p);
        }

        int projectId = Console.readInt("Enter project ID to apply: ");
        try {
            appService.applyForProject(projectId);
            System.out.println("✅ Application submitted!");
        } catch (Exception e) {
            System.out.println("❌ Failed to apply: " + e.getMessage());
        }
    }

    private void viewCurrentApplication() {
        Application app = appService.viewCurrentApplication();
        if (app == null) {
            System.out.println("No application found.");
        } else {
            System.out.println(app);
        }
    }

    private void submitEnquiry() {
        Collection<Project> projects = appService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("No projects available to submit enquiry.");
            return;
        }

        for (Project p : projects) {
            System.out.println("ID " + p.getId() + " → " + p.getProjectName());
        }

        int projectId = Console.readInt("Enter Project ID for enquiry: ");
        String question = Console.readLine("Enter your enquiry: ");
        try {
            int enquiryId = appService.submitEnquiry(question, projectId);
            System.out.println("✅ Enquiry submitted (ID: " + enquiryId + ")");
        } catch (Exception e) {
            System.out.println("❌ Failed to submit enquiry: " + e.getMessage());
        }
    }

    private void viewMyEnquiries() {
        List<Enquiry> enquiries = appService.getAllPastEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    private void editEnquiry() {
        int id = Console.readInt("Enter Enquiry ID to edit: ");
        String updated = Console.readLine("Enter new question: ");
        appService.updateEnquiry(id, updated);
        System.out.println("Enquiry updated.");
    }

    private void deleteEnquiry() {
        int id = Console.readInt("Enter Enquiry ID to delete: ");
        appService.deleteEnquiry(id);
        System.out.println("Enquiry deleted.");
    }
}
