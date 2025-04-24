package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.*;

/**
 * Manages officer registrations for a project.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>View a paginated list of officer registrations</li>
 *   <li>Approve or deny pending registrations</li>
 * </ul>
 *
 * @see PaginatedUI
 */
public class ManagerOfficerRegistrationUI {
    private final ManagerControl ctrl;
    private final PaginatedUI<Registration> paginator;

    public ManagerOfficerRegistrationUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Registration> buildPaginator() {
        return new PaginatedUI<>(Helper.toHeader("Officer Registrations"),
                () -> ctrl.getRegistrationsOfCurrentProject(), this::handleRegistrationSelection, 5,
                "No officer registrations found.");
    }

    private void handleRegistrationSelection(Registration reg) {
        MenuUI menu = new MenuUI(Helper.toHeader("Registration Details") + "\n" + reg);
        if (reg.getStatus() == RegistrationStatus.PENDING) {
            menu.addOption("Approve Registration", () -> {
                try {
                    ctrl.updateRegistrationStatus(reg.getId(), RegistrationStatus.APPROVED);
                    System.out.println("Registration approved.");
                } catch (Exception e) {
                    System.out.println("Error approving registration: " + e.getMessage());
                }
                Readers.readEnter();
                menu.exit();
            });
            menu.addOption("Deny Registration", () -> {
                try {
                    ctrl.updateRegistrationStatus(reg.getId(), RegistrationStatus.REJECTED);
                    System.out.println("Registration denied.");
                } catch (Exception e) {
                    System.out.println("Error denying registration: " + e.getMessage());
                }
                Readers.readEnter();
                menu.exit();
            });
        }
        menu.addOption("Back", menu::exit);
        menu.run();
    }
}
