package com.example.app.cli;

import java.util.List;

import com.example.app.RuntimeData;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
import com.example.app.models.Applicant;
import com.example.app.models.Project;
import com.example.app.control.ApplicantControl;
import com.example.app.enums.FlatType;

public class ApplicantProjectUI implements DataUI<Project>, BaseUI {
    private ApplicantControl applicantControl;

    ApplicantProjectUI(ApplicantControl applicantControl) {
        this.applicantControl = applicantControl;
    }

    @Override
    public void printData(List<Project> projects, int from, int to) {
        System.out.println("Available Projects:");
        for (int i = from; i <= to; i++) {
            Project project = projects.get(i - 1);
            System.out.println((i - from + 1) + ". \n" + project.toString());
        }
    }

    @Override
    public void printMenu() {
        System.out.println("6. Previous Page");
        System.out.println("7. Next Page");
        System.out.println("8. Back");
    }

    void viewProjectDetails(Project project) {
        while (true) {
            System.out.println("Project Details:");
            System.out.println(project.toString());
            System.out.println("1. Apply for Project");
            System.out.println("2. Submit Enquiry");
            System.out.println("3. Back");
            
            int choice = Readers.readIntRange(1, 3);
            Helper.wipeScreen();
            switch (choice) {
                case 1:
                    if (!applicantControl.isAbleToApply((Applicant) RuntimeData.getCurrentUser())) {
                        System.out.println("You are not eligible to apply for this project.");
                        break;
                    }
                    int flatTypeChoice = Readers.readInt("Select Flat Type (2 for 2-Room, 3 for 3-Room): ");
                    if (flatTypeChoice < 2 || flatTypeChoice > 3) {
                        System.out.println("Invalid choice. Stopping application process.");
                        break;
                    }
                    FlatType chosen = flatTypeChoice == 2 ? FlatType._2ROOM : FlatType._3ROOM;
                    try {
                        applicantControl.applyForProject((Applicant) RuntimeData.getCurrentUser(), project.getId(), chosen);
                        System.out.println("Application submitted successfully.");
                    } catch (Exception e) {
                        System.out.println("Error applying for project: " + e.getMessage());
                    }
                    break;
                case 2:
                    String message = Readers.readString("Enter your enquiry message: ");
                    try {
                        applicantControl.submitEnquiry((Applicant) RuntimeData.getCurrentUser(), message, project.getId());
                        System.out.println("Enquiry submitted successfully.");
                    } catch (Exception e) {
                        System.out.println("Error submitting enquiry: " + e.getMessage());
                    }
                case 3:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
                }
        }
    }

    @Override
    public void run() {
        List<Project> projects;
        try {
            projects = applicantControl.getViewableProjects((Applicant) RuntimeData.getCurrentUser());
        } catch (Exception e) {
            System.out.println("Error retrieving projects: " + e.getMessage());
            return;
        }
        int from = 1, to = Math.min(5, projects.size());
        while (true) {
            printData(projects, from, to);
            printMenu();
            int choice = Readers.readIntRange(1, 8);
            Helper.wipeScreen();
            switch (choice) {
                case 6: 
                    if (from > 1) {
                        from -= 5;
                        to -= 5;
                    } else {
                        System.out.println("Already on the first page.");
                    }
                    break;
                case 7:
                    if (to < projects.size()) {
                        from += 5;
                        to += 5;
                        to = Math.min(to, projects.size());
                    } else {
                        System.out.println("Already on the last page.");
                    }
                    break;
                case 8:
                    return;
                case 1, 2, 3, 4, 5:
                    if (from + choice - 1 > to) {
                        System.out.println("Invalid choice. Please select a valid project.");
                    } else {
                        Project selectedProject = projects.get(from + choice - 2);
                        System.out.println("You selected: " + selectedProject.getProjectName());
                        viewProjectDetails(selectedProject);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
    }
}