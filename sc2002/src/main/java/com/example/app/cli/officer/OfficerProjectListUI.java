package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.models.Project;
import com.example.app.models.ProjectFilter;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Displays a paginated list of projects and handles project-specific actions for officers.
 *
 * <p>This class allows officers to:
 * <ul>
 *   <li>Set filters for projects based on neighborhood, flat type, and project name</li>
 *   <li>View filtered projects</li>
 *   <li>Apply for projects</li>
 *   <li>Join projects as an officer</li>
 *   <li>Submit enquiries about projects</li>
 * </ul>
 *
 * @see PaginatedUI
 */
public class OfficerProjectListUI {
    private final OfficerControl ctrl;
    private final ProjectFilter filter;

    public OfficerProjectListUI(OfficerControl ctrl, ProjectFilter filter) {
        this.ctrl = ctrl;
        this.filter = filter;
    }

    public void run() {
        while (true) {
            Helper.wipeScreen();
            System.out.println("1. Set Filters");
            System.out.println("2. View Projects");
            System.out.println("3. View Current Filter");
            System.out.println("4. Back");
            int choice = Readers.readIntRange(1, 4);
            switch (choice) {
                case 1 -> setFilters();
                case 2 -> showProjects();
                case 3 -> showCurrentFilter();
                case 4 -> { return; }
            }
        }
    }

    private void setFilters() {
        String neighborhood = Readers.readStringAcceptEmpty("Enter neighborhood/location (leave blank for any):");
        filter.setNeighborhood(neighborhood == null || neighborhood.trim().isEmpty() ? null : neighborhood.trim());
        System.out.println("Select Flat Type: 0. Any  2. 2-Room  3. 3-Room");
        String ftInput = Readers.readStringAcceptEmpty("");
        int ftChoice = 0;
        try {
            ftChoice = ftInput == null || ftInput.trim().isEmpty() ? 0 : Integer.parseInt(ftInput.trim());
        } catch (NumberFormatException e) {
            ftChoice = 0;
        }
        switch (ftChoice) {
            case 2 -> filter.setFlatType(FlatType._2ROOM);
            case 3 -> filter.setFlatType(FlatType._3ROOM);
            default -> filter.setFlatType(null);
        }
        String projectName = Readers.readStringAcceptEmpty("Enter project name (leave blank for any):");
        filter.setProjectName(projectName == null || projectName.trim().isEmpty() ? null : projectName.trim());
    }

    private void showCurrentFilter() {
        System.out.println("Current Filter:");
        System.out.println("  Neighborhood: " + (filter.getNeighborhood() == null ? "Any" : filter.getNeighborhood()));
        System.out.println("  Flat Type: " + (filter.getFlatType() == null ? "Any" : filter.getFlatType()));
        System.out.println("  Project Name: " + (filter.getProjectName() == null ? "Any" : filter.getProjectName()));
        Readers.readEnter();
    }

    private void showProjects() {
        new PaginatedUI<Project>(Helper.toHeader("Available Projects"), this::getFilteredProjects, this::handleProjectSelection, 5, "No suitable projects found.").run();
    }

    private List<Project> getFilteredProjects() {
        List<Project> projects = ctrl.getViewableProjects();
        return projects.stream()
            .filter(p -> filter.getNeighborhood() == null || p.getNeighborhood().equalsIgnoreCase(filter.getNeighborhood()))
            .filter(p -> filter.getFlatType() == null || (p.getFlats() != null && p.getFlats().containsKey(filter.getFlatType())))
            .filter(p -> filter.getProjectName() == null || p.getProjectName().toLowerCase().contains(filter.getProjectName().toLowerCase()))
            .sorted(Comparator.comparing(Project::getProjectName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    private void handleProjectSelection(Project project) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Project Details") + "\n" + project);
        subMenu.addOption("Apply for Project", () -> {
            if (!ctrl.isAbleToApply()) {
                System.out.println("You currently have an active application and thus cannot apply for more.");
                Readers.readEnter();
            } else {
                int ftChoice = Readers.readInt("Select Flat Type (2 = 2-Room, 3 = 3-Room): ");
                FlatType ft;
                switch (ftChoice) {
                case 2:
                    ft = FlatType._2ROOM;
                    break;
                case 3:
                    ft = FlatType._3ROOM;
                    break;
                default:
                    System.out.println("Invalid choice. Stopping application process...");
                    return;
                }
                try {
                    ctrl.applyForProject(project.getId(), ft);
                    System.out.println("Application submitted.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Readers.readEnter();
            }
        });

        subMenu.addOption("Join Project as Officer", () -> {
            try {
                ctrl.registerForProject(project.getId());
                System.out.println("Successfully registered as an officer for the project.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Readers.readEnter();
        });

        subMenu.addOption("Submit Enquiry", () -> {
            String msg = Readers.readString("Enter your enquiry: ");
            try {
                ctrl.submitEnquiry(msg, project.getId());
                System.out.println("Enquiry submitted.");
            } catch (Exception e) {
                // Only print the root cause message
                System.out.println(e.getMessage());
            }
            Readers.readEnter();
        });

        subMenu.addOption("Back", subMenu::exit);

        subMenu.run();
    }
}