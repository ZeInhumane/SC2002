package com.example.app.cli;

import java.util.List;

import com.example.app.cli.utils.*;
import com.example.app.control.ApplicantControl;

import com.example.app.models.Enquiry;

public class ApplicantEnquiryUI {
    private final ApplicantControl ctrl;

    ApplicantEnquiryUI(ApplicantControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        List<Enquiry> projects = ctrl.getEnquiries();
        new PaginatedUI<Enquiry>(
            Helper.toHeader("Available Projects"),
            projects,
            this::handleEnquirySelection,
            5
        ).run();
    }

    private void handleEnquirySelection(Enquiry enquiry) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Enquiry Details") + enquiry);
        subMenu.addOption("Edit Enquiry", () -> {
            String newMessage = Readers.readString("Enter new message: ");
            try {
                ctrl.updateEnquiry(enquiry.getId(), newMessage);
                System.out.println("Enquiry updated.");
            } catch (Exception e) {
                System.out.println("Error updating enquiry: " + e.getMessage());
            }
            Readers.readEnter();
        });

        subMenu.addOption("Delete Enquiry", () -> {
            try {
                ctrl.deleteEnquiry(enquiry.getId());
                System.out.println("Enquiry deleted.");
            } catch (Exception e) {
                System.out.println("Error deleting enquiry: " + e.getMessage());
            }
            Readers.readEnter();
        });

        subMenu.addOption("Back", subMenu::exit);
        subMenu.run();
    }
}
