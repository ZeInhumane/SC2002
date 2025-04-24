package com.example.app.cli.manager;

import com.example.app.models.Manager;
import com.example.app.RuntimeData;
import com.example.app.cli.utils.*;
import com.example.app.control.UserControl;
import com.example.app.control.ManagerControl;
import com.example.app.cli.common.MenuUI;

public class ManagerUI {
    private final ManagerControl ctrl;
    private final MenuUI menu;

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
        m.addOption("View All Projects", () -> new ManagerProjectListUI(ctrl).run());
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
