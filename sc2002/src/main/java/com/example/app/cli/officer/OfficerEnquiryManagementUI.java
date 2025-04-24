package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.models.Enquiry;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

/**
 * Handles viewing and replying to enquiries for the officer's HandlingProject.
 */
public class OfficerEnquiryManagementUI {
    private final OfficerControl ctrl;

    public OfficerEnquiryManagementUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        PaginatedUI<Enquiry> paginator = new PaginatedUI<>(Helper.toHeader("Enquiries"), ctrl::getHandlingEnquiries,
                this::handleEnquirySelection, 5, "No enquiries found for your handling project.");
        paginator.run();
    }

    private void handleEnquirySelection(Enquiry enquiry) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Enquiry Details") + "\n" + enquiry);
        subMenu.addOption("Reply to Enquiry", () -> {
            String reply = Readers.readString("Enter your reply: ");
            try {
                ctrl.replyToEnquiry(enquiry.getId(), reply);
                System.out.println("Reply sent.");
                enquiry.setResponse(reply);
            } catch (Exception e) {
                System.out.println("Error sending reply: " + e.getMessage());
            }
            Readers.readEnter();
            subMenu.exit();
        });
        subMenu.addOption("Back", subMenu::exit);
        subMenu.run();
    }
}