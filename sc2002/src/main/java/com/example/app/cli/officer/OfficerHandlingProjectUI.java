package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.MenuUI;

public class OfficerHandlingProjectUI {
    private final OfficerControl ctrl;

    public OfficerHandlingProjectUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        try {
            Registration registration = ctrl.getRegistration();

            if (registration == null) {
                System.out.println("You have no current registration.");
                Readers.readEnter();
                return;
            }

            System.out.println(Helper.toHeader("Registration Details") + "\n" + registration);

            if (registration.getStatus() != RegistrationStatus.APPROVED) {
                System.out.println("Your registration status is not successful. You cannot manage this project.");
                Readers.readEnter();
                return;
            }

            Project project = ctrl.getHandlingProject();
            if (project == null) {
                System.out.println("Error: No project associated with your registration.");
                Readers.readEnter();
                return;
            }

            MenuUI menu = new MenuUI(
                    Helper.toHeader("Project Details") + "\n" + project + "\n" + Helper.toHeader("Manage Project"));
            menu.addOption("View and Reply to Enquiries", () -> new OfficerEnquiryManagementUI(ctrl).run());
            menu.addOption("View and Manage Pending Bookings", () -> new OfficerPendingBookingsUI(ctrl).run());
            menu.addOption("View and Print Successful Bookings", () -> new OfficerSuccessfulBookingsUI(ctrl).run());
            menu.addOption("Back", menu::exit);
            menu.run();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Readers.readEnter();
        }
    }
}