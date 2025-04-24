package com.example.app.cli.manager;

import com.example.app.models.Manager;
import com.example.app.RuntimeData;
import com.example.app.cli.utils.*;
import com.example.app.control.UserControl;
import com.example.app.control.ManagerControl;
import com.example.app.cli.common.MenuUI;
import com.example.app.models.ProjectFilter;

/**
 * Represents the main dashboard for managers.
 *
 * <p>This class provides a menu interface for managers to:
 * <ul>
 *   <li>View all projects</li>
 *   <li>View all enquiries</li>
 *   <li>View their projects</li>
 *   <li>Create new projects</li>
 *   <li>Generate applicant reports</li>
 *   <li>Logout from the system</li>
 * </ul>
 *
 * @see ManagerProjectListUI
 * @see ManagerAllEnquiriesUI
 * @see ManagerMyProjectListUI
 * @see ManagerCreateProjectUI
 * @see ManagerReportUI
 */
public class ManagerUI {
    private final ManagerControl ctrl;
    private final MenuUI menu;
    private final ProjectFilter projectFilter = new ProjectFilter();

    public ManagerUI() {
        Manager me = (Manager) RuntimeData.getCurrentUser();
        this.ctrl = new ManagerControl(me);
        this.menu = buildMenu();
    }

    public void run() {
        menu.run();
    }

    private MenuUI buildMenu() {
        MenuUI m = new MenuUI(Helper.toHeader("Manager Dashboard"));
        m.addOption("View All Projects", () -> new ManagerProjectListUI(ctrl, projectFilter).run());
        m.addOption("View All Enquiries", () -> new ManagerAllEnquiriesUI(ctrl).run());
        m.addOption("View My Projects", () -> new ManagerMyProjectListUI(ctrl).run());
        m.addOption("Create New Project", () -> new ManagerCreateProjectUI(ctrl).run());
        m.addOption("Generate Applicant Report", () -> new ManagerReportUI(ctrl).run());
        m.addOption("Logout", () -> {
            UserControl.logout();
            Readers.readEnter();
            m.exit();
        });
        return m;
    }
}
