package com.example.app.cli;

import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.service.OfficerService;
import com.example.app.utils.Console;

import java.util.Collection;
import java.util.List;

public class OfficerCLI {
    private final OfficerService officerService;

    public OfficerCLI(OfficerService officerService) {
        this.officerService = officerService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Officer Menu (includes Applicant features) ===");
            System.out.println("1) View Available Projects");
            System.out.println("2) Apply for Project");
            System.out.println("3) View My Application");
            System.out.println("4) Submit Enquiry");
            System.out.println("5) View My Enquiries");
            System.out.println("6) Edit Enquiry");
            System.out.println("7) Delete Enquiry");
            System.out.println("8) View Project Enquiries (Officer)");
            System.out.println("9) View Enquiry by ID (Officer)");
            System.out.println("10) Reply to Enquiry (Officer)");
            System.out.println("11) Register as Officer for Project");
            System.out.println("12) View My Officer Registration");
            System.out.println("0) Logout");

            int choice = Console.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> viewProjects();
                case 2 -> applyForProject();
                case 3 -> viewCurrentApplication();
                case 4 -> submitEnquiry();
                case 5 -> viewMyEnquiries();
                case 6 -> editEnquiry();
                case 7 -> deleteEnquiry();
                case 8 -> viewProjectEnquiries();
                case 9 -> viewEnquiryById();
                case 10 -> replyToEnquiry();
                case 11 -> registerAsOfficer();
                case 12 -> viewMyRegistration();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // === APPLICANT FEATURES ===

    private void viewProjects() {
        Collection<Project> projects = officerService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("📭 No available projects at the moment.");
        } else {
            System.out.println("=== Available Projects ===");
            for (Project p : projects) {
                System.out.println(p);
            }
        }
    }

    private void applyForProject() {
        if (!officerService.isAbleToApply()) {
            System.out.println("⚠️ You already have an active or successful application.");
            return;
        }

        Collection<Project> projects = officerService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("📭 No available projects to apply for at the moment.");
            return;
        }

        viewProjects();
        int projectId = Console.readInt("Enter project ID to apply: ");
        try {
            officerService.applyForProject(projectId);
            System.out.println("✅ Application submitted!");
        } catch (Exception e) {
            System.out.println("❌ Failed to apply: " + e.getMessage());
        }
    }

    private void viewCurrentApplication() {
        Application app = officerService.viewCurrentApplication();
        if (app == null) {
            System.out.println("No application found.");
        } else {
            System.out.println(app);
        }
    }

    private void submitEnquiry() {
        Collection<Project> projects = officerService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("📭 No projects to enquire about.");
            return;
        }

        viewProjects();
        int projectId = Console.readInt("Enter Project ID: ");
        String question = Console.readLine("Enter your enquiry: ");
        try {
            int enquiryId = officerService.submitEnquiry(question, projectId);
            System.out.println("✅ Enquiry submitted with ID: " + enquiryId);
        } catch (Exception e) {
            System.out.println("❌ Failed to submit enquiry: " + e.getMessage());
        }
    }

    private void viewMyEnquiries() {
        List<Enquiry> enquiries = officerService.getAllPastEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("📭 You have no enquiries.");
            return;
        }

        System.out.println("=== Your Enquiries ===");
        for (Enquiry e : enquiries) {
            System.out.println(e);
        }
    }

    private void editEnquiry() {
        List<Enquiry> enquiries = officerService.getAllPastEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("📭 You have no enquiries to edit.");
            return;
        }

        for (Enquiry e : enquiries) {
            System.out.println(e);
        }

        int id = Console.readInt("Enter Enquiry ID to edit: ");
        String updated = Console.readLine("Enter new question: ");
        try {
            officerService.updateEnquiry(id, updated);
            System.out.println("✅ Enquiry updated.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void deleteEnquiry() {
        List<Enquiry> enquiries = officerService.getAllPastEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("📭 You have no enquiries to delete.");
            return;
        }

        for (Enquiry e : enquiries) {
            System.out.println(e);
        }

        int id = Console.readInt("Enter Enquiry ID to delete: ");
        try {
            officerService.deleteEnquiry(id);
            System.out.println("✅ Enquiry deleted.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // === OFFICER-SPECIFIC FEATURES ===

    private void viewProjectEnquiries() {
        try {
            List<Enquiry> enquiries = officerService.getProjectEnquiries();
            if (enquiries.isEmpty()) {
                System.out.println("📭 No enquiries submitted for your project.");
            } else {
                System.out.println("=== Enquiries for Your Project ===");
                for (Enquiry e : enquiries) {
                    System.out.println(e);
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void viewEnquiryById() {
        int id = Console.readInt("Enter Enquiry ID: ");
        Enquiry e = officerService.getEnquiry(id);
        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("❌ Enquiry not found.");
        }
    }

    private void replyToEnquiry() {
        int id = Console.readInt("Enter Enquiry ID: ");
        String reply = Console.readLine("Enter reply: ");
        try {
            officerService.replyEnquiry(id, reply);
            System.out.println("✅ Reply submitted.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void registerAsOfficer() {
        int projectId = Console.readInt("Enter Project ID to register for: ");
        if (officerService.isNotRegisterableAsOfficer(projectId)) {
            System.out.println("❌ You cannot register for this project. You're either an applicant or already handling another.");
            return;
        }

        officerService.registerAsOfficer(projectId);
        System.out.println("✅ Registered as officer for project ID: " + projectId);
    }

    private void viewMyRegistration() {
        try {
            Registration r = officerService.viewCurrentRegistration();
            System.out.println("=== Your Registration ===");
            System.out.println("Project ID: " + r.getProjectId());
            System.out.println("Status: " + r.getStatus());
        } catch (Exception e) {
            System.out.println("❌ You are not registered for any project.");
        }
    }
}

