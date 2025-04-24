package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.models.Application;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

/**
 * Handles viewing and booking pending applications for the officer's HandlingProject.
 */
public class OfficerPendingBookingsUI {
    private final OfficerControl ctrl;

    public OfficerPendingBookingsUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        PaginatedUI<Application> paginator = new PaginatedUI<Application>(
            Helper.toHeader("Pending Bookings"),
            ctrl::getBookingApplications,
            this::handleApplicationSelection,
            5,
            "No pending bookings found for your handling project."
        );
        paginator.run();
    }

    private void handleApplicationSelection(Application application) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Application Details") + "\n" + application);
        subMenu.addOption("Book Applicant", () -> {
            try {
                ctrl.bookFlatForApplicant(application.getUserId());
                System.out.println("Applicant booked successfully.");
            } catch (Exception e) {
                System.out.println("Error booking applicant: " + e.getMessage());
            }
            Readers.readEnter();
        });
        subMenu.addOption("Back", subMenu::exit);
        subMenu.run();
    }
}