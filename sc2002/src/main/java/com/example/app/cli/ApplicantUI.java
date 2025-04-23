package com.example.app.cli;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.Project;
import com.example.app.service.impl.ApplicantServiceImpl;
import com.example.app.RuntimeData;
import com.example.app.enums.ApplicationStatus;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

public class ApplicantUI {
    public void run() {
        Applicant me = (Applicant) RuntimeData.getCurrentUser();
        ApplicantControl ctrl = new ApplicantControl(new ApplicantServiceImpl(), me);
        
        MenuUI menu = new MenuUI(Helper.toHeader("Applicant Dashboard"));

        menu.addOption("View Projects", () ->
            new ApplicantProjectListUI(ctrl).run()
        );

        menu.addOption("View Applied Project", () -> {
            Application app = ctrl.getApplication();
            if (app == null) {
                System.out.println("No applied project.");
                Readers.readEnter();
                return;
            }

            // print out both project & application details
            Project p = ctrl.getAppliedProject();
            System.out.println("Project Details:\n" + p);
            System.out.println("Application Details:\n" + app);
            
            if (app.getStatus() == ApplicationStatus.WITHDRAWN) {
                System.out.println("You have already withdrawn this application.");
                Readers.readEnter();
                return;
            }
            
            MenuUI subMenu = new MenuUI("Applied Project: " + p.getProjectName());
            subMenu.addOption("Withdraw", () -> {
                try {
                    ctrl.withdrawApplication();
                    System.out.println("Application withdrawn.");
                } catch (Exception e) {
                    System.out.println("Error withdrawing application: " + e.getMessage());
                }
                Readers.readEnter();
                subMenu.exit();
            });
            subMenu.addOption("Back", subMenu::exit);
            subMenu.run();
        });

        menu.addOption("View Enquiries", () -> {
            new ApplicantEnquiryUI(ctrl).run();
        });

        menu.addOption("Back", menu::exit);
        menu.run();
    }
}
