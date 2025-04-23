package com.example.app.cli;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Project;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.*;
import java.util.List;

/**
 * Displays a paginated list of projects and handles project-specific actions.
 */
public class ApplicantProjectListUI {
    private final ApplicantControl ctrl;

    public ApplicantProjectListUI(ApplicantControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        List<Project> projects = ctrl.getViewableProjects();
        new PaginatedUI<Project>(
            Helper.toHeader("Available Projects"),
            projects,
            this::handleProjectSelection,
            5
        ).run();
    }

    private void handleProjectSelection(Project project) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Project Details") + project);
        subMenu.addOption("Apply for Project", () -> {
            if (!ctrl.isAbleToApply()) {
                System.out.println("You currently have an active application and thus cannot apply for more.");
                Readers.readEnter();
            } else {
                int ftChoice = Readers.readInt("Select Flat Type (2=2-Room, 3=3-Room): ");
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
                    System.out.println("Error submitting application: " + e.getMessage());
                }
                Readers.readEnter();
            }
        });

        subMenu.addOption("Submit Enquiry", () -> {
            String msg = Readers.readString("Enter your enquiry: ");
            try {
                ctrl.submitEnquiry(msg, project.getId());
                System.out.println("Enquiry submitted.");
            } catch (Exception e) {
                System.out.println("Error submitting enquiry: " + e.getMessage());
            }
            Readers.readEnter();
        });

        subMenu.addOption("Back", subMenu::exit);

        subMenu.run();
    }
}