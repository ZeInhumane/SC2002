package com.example.app.cli.applicant;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Project;
import com.example.app.models.Application;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

public class ApplicantAppliedProjectUI {
    private final ApplicantControl ctrl;

    public ApplicantAppliedProjectUI(ApplicantControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        Project project = ctrl.getAppliedProject();
        Application application = ctrl.getApplication();
        if (project == null || application == null) {
            System.out.println("You have no current application.");
            Readers.readEnter();
            return;
        }
        MenuUI menu = new MenuUI(Helper.toHeader("Application Details") + "\n" + application + "\n"
                + Helper.toHeader("Project Details") + "\n" + project);
        menu.addOption("Withdraw Application", () -> {
            try {
                ctrl.withdrawApplication();
                System.out.println("Application withdrawn.");
            } catch (Exception e) {
                System.out.println("Error withdrawing application: " + e.getMessage());
            }
            Readers.readEnter();
        });
        menu.addOption("Back", menu::exit);
        menu.run();
    }
}