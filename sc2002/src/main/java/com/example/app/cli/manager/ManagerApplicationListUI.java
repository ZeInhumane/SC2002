package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Application;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
import com.example.app.cli.common.*;

public class ManagerApplicationListUI {
    private final ManagerControl ctrl;
    private final int projectId;
    private final PaginatedUI<Application> paginator;

    public ManagerApplicationListUI(ManagerControl ctrl, int projectId) {
        this.ctrl = ctrl;
        this.projectId = projectId;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Application> buildPaginator() {
        return new PaginatedUI<>(
            Helper.toHeader("Project Applications"),
            () -> ctrl.getApplicationsOfProject(projectId),
            this::handleApplicationSelection,
            5,
            "No applications found."
        );
    }

    private void handleApplicationSelection(Application app) {
        MenuUI menu = new MenuUI(Helper.toHeader("Application Details") + "\n" + app);
        if (app.getStatus() == com.example.app.enums.ApplicationStatus.PENDING) {
            menu.addOption("Approve Application", () -> {
                try {
                    ctrl.updateApplicationStatus(app.getId(), true);
                    System.out.println("Application approved.");
                } catch (Exception e) {
                    System.out.println("Error approving application: " + e.getMessage());
                }
                Readers.readEnter();
                menu.exit();
            });
        }
        if (app.isRequestWithdrawal()) {
            menu.addOption("Approve Withdrawal", () -> {
                try {
                    ctrl.updateWithdrawalStatus(app.getId(), true);
                    System.out.println("Withdrawal approved.");
                } catch (Exception e) {
                    System.out.println("Error approving withdrawal: " + e.getMessage());
                }
                Readers.readEnter();
                menu.exit();
            });
        }
        menu.addOption("Back", menu::exit);
        menu.run();
    }
}
