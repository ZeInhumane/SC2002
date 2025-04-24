package com.example.app.cli.officer;

import com.example.app.cli.utils.*;
import com.example.app.control.OfficerControl;
import com.example.app.models.Enquiry;
import com.example.app.cli.common.*;

/**
 * Manages and displays a paginated list of enquiries for officers.
 *
 * <p>This class allows officers to:
 * <ul>
 *   <li>View their submitted enquiries</li>
 *   <li>Edit an existing enquiry</li>
 *   <li>Delete an enquiry</li>
 * </ul>
 *
 * @see PaginatedUI
 */
public class OfficerEnquiryUI {
    private final OfficerControl ctrl;
    private final PaginatedUI<Enquiry> paginator;

    public OfficerEnquiryUI(OfficerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Enquiry> buildPaginator() {
        return new PaginatedUI<>(Helper.toHeader("Your Enquiries"), ctrl::getEnquiries, this::handleEnquirySelection, 5,
                "No enquiries found.");
    }

    private void handleEnquirySelection(Enquiry enquiry) {
        MenuUI subMenu = new MenuUI(Helper.toHeader("Enquiry Details") + "\n" + enquiry);
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