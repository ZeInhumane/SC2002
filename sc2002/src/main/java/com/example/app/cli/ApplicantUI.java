package com.example.app.cli;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.Project;
import com.example.app.service.impl.ApplicantServiceImpl;
import com.example.app.RuntimeData;
import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
import java.util.List;

public class ApplicantUI {
    public void run() {
        ApplicantControl ctrl = new ApplicantControl(new ApplicantServiceImpl());
        Applicant me = (Applicant) RuntimeData.getCurrentUser();
        MenuUI menu = new MenuUI("Applicant Dashboard");

        menu.addOption("View Projects", () ->
            new ApplicantProjectListUI(ctrl).run()
        );

        menu.addOption("View Applied Project", () -> {
            Application app = ctrl.getApplication(me);
            if (app == null) {
                System.out.println("No applied project.");
                Readers.readString("Press ENTER to continue...");
                return;
            }

            // print out both project & application details
            Project p = ctrl.getAppliedProject(me);
            System.out.println("Project Details:\n" + p);
            System.out.println("Application Details:\n" + app);
            
            if (app.getStatus() == ApplicationStatus.WITHDRAWN) {
                System.out.println("You have already withdrawn this application.");
                Readers.readString("Press ENTER to continue...");
                return;
            }
            
            MenuUI subMenu = new MenuUI("Applied Project: " + p.getProjectName());
            subMenu.addOption("Withdraw", () -> {
                try {
                    ctrl.withdrawApplication(me);
                    System.out.println("Application withdrawn.");
                } catch (Exception e) {
                    System.out.println("Error withdrawing application: " + e.getMessage());
                }
                Readers.readString("Press ENTER to continue...");
                subMenu.exit();
            });
            subMenu.addOption("Back", subMenu::exit);
            subMenu.run();
        });

        menu.addOption("View Enquiries", () -> {
            // TODO: Implement enquiry listing UI
        });

        menu.addOption("Back", menu::exit);
        menu.run();
    }
}
