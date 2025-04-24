package com.example.app.cli.officer;

import com.example.app.control.OfficerControl;
import com.example.app.control.UserControl;
import com.example.app.models.Officer;
import com.example.app.RuntimeData;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.MenuUI;
import com.example.app.models.ProjectFilter;

public class OfficerUI {
    private final OfficerControl ctrl;
    private final MenuUI menu;
    private final ProjectFilter projectFilter = new ProjectFilter();

    public OfficerUI() {
        Officer me = (Officer) RuntimeData.getCurrentUser();
        this.ctrl = new OfficerControl(me);
        this.menu = buildMenu();
    }

    public void run() {
        menu.run();
    }

    private MenuUI buildMenu() {
        MenuUI m = new MenuUI(Helper.toHeader("Officer Dashboard"));
        m.addOption("View All Projects", () -> new OfficerProjectListUI(ctrl, projectFilter).run());
        m.addOption("View Applied Project", () -> new OfficerAppliedProjectUI(ctrl).run());
        m.addOption("View Enquiries", () -> new OfficerEnquiryUI(ctrl).run());
        m.addOption("View Handling Project", () -> new OfficerHandlingProjectUI(ctrl).run());
        m.addOption("Logout", () -> {
            UserControl.logout();
            Readers.readEnter();
            m.exit();
        });
        return m;
    }
}