package com.example.app.cli;

import com.example.app.models.*;
import com.example.app.service.ManagerService;
import com.example.app.utils.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerCLI {
    private final ManagerService managerService;

    public ManagerCLI(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void run() {
        while (true) {
            managerService.reassignManaging();
            System.out.println("\n=== HDB Manager Menu ===");
            System.out.println("1) View Projects");
            System.out.println("2) Create Project");
            System.out.println("3) Delete Project");
            System.out.println("4) Update Project");
            System.out.println("5) Handle Officer Registrations");
            System.out.println("0) Logout");

            int choice = Console.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> viewProjectsMenu();
                case 2 -> createProject();
                case 3 -> deleteProject();
                case 4 -> updateProjectMenu();
                case 5 -> handleRegistrations();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewProjectsMenu() {
        System.out.println("\n1) View All Projects");
        System.out.println("2) View My Projects");
        System.out.println("3) View Currently Handling Project");

        int choice = Console.readInt("Choose an option: ");
        switch (choice) {
            case 1 -> viewAllProjects();
            case 2 -> viewMyProjects();
            case 3 -> viewHandlingProject();
            default -> System.out.println("Invalid option.");
        }
    }


    // View all projects
    private void viewAllProjects() {
        Collection<Project> projects = managerService.viewProjects();
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }
        System.out.println("=== All Projects ===");
        for (Project p : projects) {
            System.out.println(p);
        }
    }


    // view all of the managers projects
    private void viewMyProjects() {
        Collection<Project> projects = managerService.viewMyProjects();
        if (projects.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }
        System.out.println("=== My Projects ===");
        for (Project p : projects) {
            System.out.println(p);
        }
    }


    // View the current project the manager is handling 
    private void viewHandlingProject() {
        Project current = managerService.viewHandlingProject();
        if (current == null) {
            System.out.println("You are not currently handling any project.");
        } else {
            System.out.println("=== Currently Handling Project ===");
            System.out.println(current);
        }
    }




    // Create New Project
    private void createProject() {
        System.out.println("=== Create New BTO Project ===");

        String projectName = Console.readLine("Enter project name: ");
        String neighborhood = Console.readLine("Enter neighborhood: ");

        System.out.println("Eligible group options:");
        for (MaritalStatus status : MaritalStatus.values()) {
            System.out.println("- " + status);
        }

        String groupInput = Console.readLine("Enter eligible group: ");
        MaritalStatus group;
        try {
            group = MaritalStatus.valueOf(groupInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid group. Please enter one of the listed options.");
            return;
        }

        String openDateStr = Console.readLine("Enter application open date (yyyy-MM-dd): ");
        String closeDateStr = Console.readLine("Enter application close date (yyyy-MM-dd): ");
        Date applicationOpenDate;
        Date applicationCloseDate;
        try {
            applicationOpenDate = new SimpleDateFormat("yyyy-MM-dd").parse(openDateStr);
            applicationCloseDate = new SimpleDateFormat("yyyy-MM-dd").parse(closeDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        if (managerService.hasDateOverlap(applicationOpenDate, applicationCloseDate)) {
            System.out.println("You already have a project with an overlapping application period. Please choose a different date range.");
            return;
        }

        Map<FlatType, Integer> flats = new HashMap<>();
        System.out.println("Enter unit count for each flat type:");
        for (FlatType type : FlatType.values()) {
            int count = Console.readInt("- " + type + ": ");
            flats.put(type, count);
        }

        try {
            managerService.createProject(projectName, applicationOpenDate, applicationCloseDate, neighborhood, group, flats);
            System.out.println("Project created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create project: " + e.getMessage());
        }
    }

    // Delete Project
    private void deleteProject() {
        System.out.println("=== Delete BTO Project ===");

        Collection<Project> myProjects = managerService.viewMyProjects();

        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects to delete.");
            return;
        }

        System.out.println("=== Your Projects ===");
        for (Project p : myProjects) {
            System.out.println(p);
        }

        int projectId = Console.readInt("Enter Project ID to delete: ");
        if (!managerService.isProjectBelongToManager(projectId)) {
            System.out.println("You no project with that Id");
            return;
        }
        String confirm = Console.readLine("Are you sure you want to delete this project? (yes/no): ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        try {
            managerService.deleteProject(projectId);
            System.out.println("Project deleted successfully.");
        } catch (Exception e) {
            System.out.println("Failed to delete project: " + e.getMessage());
        }

    }


private void updateProjectMenu() {
        System.out.println("=== Update Project Menu ===");
        System.out.println("1) Update Project Particulars");
        System.out.println("2) Toggle Project Visibility");

        int choice = Console.readInt("Choose an option: ");
        switch (choice) {
            case 1 -> updateProjectParticulars();
            case 2 -> toggleProjectVisibility();
            default -> System.out.println("Invalid option.");
        }
    }

    private void toggleProjectVisibility() {
        Collection<Project> myProjects = managerService.viewMyProjects();
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }

        System.out.println("=== Your Projects ===");
        for (Project p : myProjects) {
            System.out.println(p);
        }

        int projectId = Console.readInt("Enter Project ID to toggle visibility: ");
        if (!managerService.isProjectBelongToManager(projectId)) {
            System.out.println("You no project with that Id");
            return;
        }
        try {
            managerService.toggleVisibility(projectId);
            System.out.println("Visibility toggled successfully.");
        } catch (Exception e) {
            System.out.println("Failed to toggle visibility: " + e.getMessage());
        }
    }

    private void updateProjectParticulars() {
        System.out.println("=== Update BTO Project ===");

        Collection<Project> myProjects = managerService.viewMyProjects();
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }

        System.out.println("=== Your Projects ===");
        for (Project p : myProjects) {
            System.out.println(p);
        }

        int projectId = Console.readInt("Enter Project ID to update: ");
        if (!managerService.isProjectBelongToManager(projectId)) {
            System.out.println("You no project with that Id");
            return;
        }
        Project project = managerService.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.println("Press enter to keep the existing value.");

        String projectName = Console.readLine("Project Name [%s]: ".formatted(project.getProjectName()));
        if (projectName.isBlank()) projectName = project.getProjectName();

        String neighborhood = Console.readLine("Neighborhood [%s]: ".formatted(project.getNeighborhood()));
        if (neighborhood.isBlank()) neighborhood = project.getNeighborhood();

        System.out.println("Eligible group options:");
        for (MaritalStatus status : MaritalStatus.values()) {
            System.out.println("- " + status);
        }

        String groupInput = Console.readLine("Eligible Group [%s]: ".formatted(project.getGroup()));
        MaritalStatus group;
        try {
            group = groupInput.isBlank() ? project.getGroup() : MaritalStatus.valueOf(groupInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid group.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String openDateStr = Console.readLine("Application Open Date [%s]: ".formatted(sdf.format(project.getApplicationOpenDate())));
        String closeDateStr = Console.readLine("Application Close Date [%s]: ".formatted(sdf.format(project.getApplicationCloseDate())));
        Date openDate, closeDate;
        try {
            openDate = openDateStr.isBlank() ? project.getApplicationOpenDate() : sdf.parse(openDateStr);
            closeDate = closeDateStr.isBlank() ? project.getApplicationCloseDate() : sdf.parse(closeDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        if (managerService.hasDateOverlap(openDate, closeDate)) {
            System.out.println("You already have another project with an overlapping application period.");
            System.out.println("Please choose a different date range.");
            return;
        }

        Map<FlatType, Integer> flats = new HashMap<>();
        System.out.println("Update unit count for each flat type (leave blank to keep current):");
        for (FlatType type : FlatType.values()) {
            String current = String.valueOf(project.getFlats().getOrDefault(type, 0));
            String input = Console.readLine("- %s units [%s]: ".formatted(type.name(), current));
            int count = input.isBlank() ? Integer.parseInt(current) : Integer.parseInt(input);
            flats.put(type, count);
        }

        String visInput = Console.readLine("Visibility ON/OFF [%s]: ".formatted(project.getVisibility() ? "ON" : "OFF"));
        boolean visibility = visInput.isBlank() ? project.getVisibility() : visInput.equalsIgnoreCase("ON");

        try {
            managerService.editProject(projectId, projectName, openDate, closeDate, neighborhood, group, flats, visibility);
            System.out.println("Project updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update project: " + e.getMessage());
        }
    }


    private void handleRegistrations() {
        Collection<Project> myProjects = managerService.viewMyProjects();
        if (myProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
            return;
        }

        System.out.println("=== Your Projects ===");
        for (Project p : myProjects) {
            System.out.println(p);
        }

        int projectId = Console.readInt("Enter Project ID to manage registrations: ");
        if (!managerService.isProjectBelongToManager(projectId)) {
            System.out.println("You are not authorized to manage this project's registrations.");
            return;
        }

        List<Registration> registrations = managerService.viewRegistrations(projectId);
        if (registrations.isEmpty()) {
            System.out.println("No registrations for this project.");
            return;
        }

        System.out.println("=== Pending Officer Registrations ===");
        for (Registration r : registrations) {
            if (r.getStatus() == RegistrationStatus.PENDING) {
                System.out.println(r);
            }
        }

        while (true) {
            int regId = Console.readInt("Enter Registration ID to approve/reject (or -1 to exit): ");
            if (regId == -1) break;

            String action = Console.readLine("Approve or Reject? (a/r): ");
            if (action.equalsIgnoreCase("a")) {
                managerService.approveRegistration(regId);
                System.out.println("Registration approved.");
            } else if (action.equalsIgnoreCase("r")) {
                managerService.rejectRegistration(regId);
                System.out.println("Registration rejected.");
            } else {
                System.out.println("Invalid action.");
            }
        }
    }
}
