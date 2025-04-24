package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.models.Application;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

/**
 * Handles viewing and printing successful bookings for the officer's HandlingProject.
 */
public class OfficerSuccessfulBookingsUI {
    private final OfficerControl ctrl;

    public OfficerSuccessfulBookingsUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        PaginatedUI<Application> paginator = new PaginatedUI<>(Helper.toHeader("Successful Bookings"),
                ctrl::getBookingApplications, this::handleApplicationSelection, 5,
                "No successful bookings found for your handling project.");
        paginator.run();
    }

    private void handleApplicationSelection(Application application) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Application Details") + "\n" + application);
        subMenu.addOption("Print Booking Receipt", () -> {
            try {
                String receipt = ctrl.generateBookingReceipt(application.getUserId());
                System.out.println("Booking Receipt:\n" + receipt);
            } catch (Exception e) {
                System.out.println("Error generating receipt: " + e.getMessage());
            }
            Readers.readEnter();
        });
        subMenu.addOption("Back", subMenu::exit);
        subMenu.run();
    }
}