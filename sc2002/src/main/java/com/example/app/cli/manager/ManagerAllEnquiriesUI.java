package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Enquiry;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.common.PaginatedUI;

/**
 * Displays a paginated list of all enquiries across projects.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>View all enquiries without specific actions on selection</li>
 * </ul>
 *
 * @see PaginatedUI
 */
public class ManagerAllEnquiriesUI {
    private final ManagerControl ctrl;
    private final PaginatedUI<Enquiry> paginator;

    public ManagerAllEnquiriesUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Enquiry> buildPaginator() {
        return new PaginatedUI<>(Helper.toHeader("All Enquiries"), ctrl::getAllEnquiries, enquiry -> {
        }, // No action on selection
                5, "No enquiries found.");
    }
}
