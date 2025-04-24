package com.example.app.cli;

import com.example.app.control.OfficerControl;
import com.example.app.models.Project;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.*;

/**
 * Displays a paginated list of projects and handles project-specific actions for officers.
 */
public class OfficerProjectListUI {
    private final OfficerControl ctrl;
    private final PaginatedUI<Project> paginator;

    public OfficerProjectListUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Project> buildPaginator() {
        return new PaginatedUI<>(
            Helper.toHeader("Available Projects"),
            ctrl::getViewableProjects,
            this::handleProjectSelection,
            5,
            "No suitable projects found."
        );
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
                    System.out.println("Error submitting application: " + e.getMessage());
                }
                Readers.readEnter();
            }
        });

        subMenu.addOption("Join Project as Officer", () -> {
            try {
                ctrl.registerForProject(project.getId());
                System.out.println("Successfully registered as an officer for the project.");
            } catch (Exception e) {
                System.out.println("Error registering as officer: " + e.getMessage());
            }
            Readers.readEnter();
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