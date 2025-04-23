package com.example.app.cli;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Applicant;
import com.example.app.models.Project;
import com.example.app.RuntimeData;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
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
        Applicant me = (Applicant) RuntimeData.getCurrentUser();
        List<Project> projects = ctrl.getViewableProjects(me);
        new PaginatedUI<>(
            "Available Projects",
            projects,
            this::handleProjectSelection,
            5
        ).run();
    }

    private void handleProjectSelection(Project project) {
        while (true) {
            Helper.wipeScreen();
            System.out.println("== Project Details ==");
            System.out.println(project);
            System.out.println("1. Apply for Project");
            System.out.println("2. Submit Enquiry");
            System.out.println("3. Back");

            int choice = Readers.readIntRange(1, 3);
            Helper.wipeScreen();
            switch (choice) {
                case 1:
                    if (!ctrl.isAbleToApply((Applicant) RuntimeData.getCurrentUser())) {
                        System.out.println("You currently have an active application and thus cannot apply for more.");
                        Readers.readString("Enter to continue...");
                    } else {
                        int ftChoice = Readers.readInt("Select Flat Type (2=2‑Room, 3=3‑Room): ");
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
                                continue;
                        }
                        ctrl.applyForProject((Applicant) RuntimeData.getCurrentUser(),
                                             project.getId(), ft);
                        System.out.println("Application submitted.");
                        Readers.readString("Enter to continue...");
                    }
                    break;
                case 2:
                    String msg = Readers.readString("Enter your enquiry: ");
                    ctrl.submitEnquiry((Applicant) RuntimeData.getCurrentUser(),
                                       msg, project.getId());
                    System.out.println("Enquiry submitted.");
                    Readers.readString("Enter to continue...");
                    break;
                default:
                    return;
            }
        }
    }
}