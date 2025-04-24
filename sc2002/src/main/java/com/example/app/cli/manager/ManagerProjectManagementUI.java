package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Project;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.MenuUI;
import java.util.Date;

/**
 * Manages individual projects for managers.
 *
 * <p>This class provides options to:
 * <ul>
 *   <li>Edit project details</li>
 *   <li>View officer registrations</li>
 *   <li>View applications</li>
 *   <li>View enquiries</li>
 *   <li>Toggle project visibility</li>
 * </ul>
 */
public class ManagerProjectManagementUI {
    private final ManagerControl ctrl;
    private final Project project;

    public ManagerProjectManagementUI(ManagerControl ctrl, Project project) {
        this.ctrl = ctrl;
        this.project = project;
    }

    public void run() {
        MenuUI menu = new MenuUI(Helper.toHeader("Manage Project") + "\n" + project);
        Date now = new Date();
        if (now.before(project.getApplicationOpenDate())) {
            menu.addOption("Edit Project", this::editProject);
            menu.addOption("Back", menu::exit);
            menu.run();
            return;
        }
        if (now.after(project.getApplicationOpenDate()) && now.before(project.getApplicationCloseDate())) {
            menu.addOption("View Officer Registrations", this::viewOfficerRegistrations);
        }
        menu.addOption("View Applications", this::viewApplications);
        menu.addOption("View Enquiries", this::viewEnquiries);
        menu.addOption("Toggle Project Visibility", this::toggleVisibility);
        menu.addOption("Back", menu::exit);
        menu.run();
    }

    private void editProject() {
        try {
            System.out.println("\nEditing Project: " + project.getProjectName());
            String name = Readers.readString("Enter new project name (current: " + project.getProjectName() + "):");
            String neighborhood = Readers
                    .readString("Enter new neighborhood (current: " + project.getNeighborhood() + "):");
            String openDateStr = Readers.readString("Enter new application open date (yyyy-MM-dd, current: "
                    + project.formatDate(project.getApplicationOpenDate()) + "):");
            String closeDateStr = Readers.readString("Enter new application close date (yyyy-MM-dd, current: "
                    + project.formatDate(project.getApplicationCloseDate()) + "):");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date openDate = sdf.parse(openDateStr);
            java.util.Date closeDate = sdf.parse(closeDateStr);
            boolean visibility = project.getVisibility(); // Keep as is

            // Officer limit
            int officerLimit = Readers
                    .readInt("Enter officer limit (max 10, current: " + project.getOfficerLimit() + "):");
            if (officerLimit < 1 || officerLimit > 10) {
                System.out.println("Officer limit must be between 1 and 10.");
                Readers.readEnter();
                return;
            }

            // Flat counts
            int twoRoom = Readers.readInt("Enter number of 2-Room units (current: "
                    + project.getFlats().getOrDefault(com.example.app.enums.FlatType._2ROOM, 0) + "):");
            int threeRoom = Readers.readInt("Enter number of 3-Room units (current: "
                    + project.getFlats().getOrDefault(com.example.app.enums.FlatType._3ROOM, 0) + "):");
            java.util.Map<com.example.app.enums.FlatType, Integer> flats = new java.util.HashMap<>();
            flats.put(com.example.app.enums.FlatType._2ROOM, twoRoom);
            flats.put(com.example.app.enums.FlatType._3ROOM, threeRoom);

            // Marital status groups
            System.out.println("Select eligible groups (comma separated, current: " + project.formatGroup() + "):");
            System.out.println("1. MARRIED\n2. SINGLE");
            String groupInput = Readers.readString("Enter choices (e.g. 1,2):");
            java.util.Set<com.example.app.enums.MaritalStatus> groups = new java.util.HashSet<>();
            for (String s : groupInput.split(",")) {
                s = s.trim();
                if (s.equals("1"))
                    groups.add(com.example.app.enums.MaritalStatus.MARRIED);
                if (s.equals("2"))
                    groups.add(com.example.app.enums.MaritalStatus.SINGLE);
            }
            if (groups.isEmpty()) {
                System.out.println("At least one group must be selected.");
                Readers.readEnter();
                return;
            }

            ctrl.updateProject(project.getId(), name, openDate, closeDate, neighborhood, visibility, groups, flats);
            System.out.println("Project updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating project: " + e.getMessage());
        }
        Readers.readEnter();
    }

    private void viewOfficerRegistrations() {
        new ManagerOfficerRegistrationUI(ctrl).run();
    }

    private void viewApplications() {
        new ManagerApplicationListUI(ctrl, project.getId()).run();
    }

    private void viewEnquiries() {
        new ManagerEnquiryListUI(ctrl, project.getId()).run();
    }

    private void toggleVisibility() {
        try {
            ctrl.toggleVisibility(project.getId());
            System.out.println("Project visibility toggled.");
        } catch (Exception e) {
            System.out.println("Error toggling visibility: " + e.getMessage());
        }
        Readers.readEnter();
    }
}
