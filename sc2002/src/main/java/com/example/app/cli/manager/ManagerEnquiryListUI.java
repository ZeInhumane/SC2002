package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Enquiry;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

/**
 * Displays enquiries for a specific project.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>View a paginated list of enquiries for a project</li>
 *   <li>Reply to specific enquiries</li>
 * </ul>
 *
 * @see PaginatedUI
 */
public class ManagerEnquiryListUI {
    private final ManagerControl ctrl;
    private final int projectId;
    private final PaginatedUI<Enquiry> paginator;

    public ManagerEnquiryListUI(ManagerControl ctrl, int projectId) {
        this.ctrl = ctrl;
        this.projectId = projectId;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Enquiry> buildPaginator() {
        return new PaginatedUI<>(Helper.toHeader("Project Enquiries"), () -> ctrl.getEnquiriesOfProject(projectId),
                this::handleEnquirySelection, 5, "No enquiries found.");
    }

    private void handleEnquirySelection(Enquiry enquiry) {
        MenuUI menu = new MenuUI(Helper.toHeader("Enquiry Details") + "\n" + enquiry);
        menu.addOption("Reply to Enquiry", () -> {
            String reply = Readers.readString("Enter your reply:");
            try {
                ctrl.replyEnquiry(enquiry.getId(), reply);
                System.out.println("Reply sent.");
            } catch (Exception e) {
                System.out.println("Error replying to enquiry: " + e.getMessage());
            }
            Readers.readEnter();
            menu.exit();
        });
        menu.addOption("Back", menu::exit);
        menu.run();
    }
}
