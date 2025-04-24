package com.example.app.cli.applicant;

import com.example.app.control.ApplicantControl;
import com.example.app.models.Applicant;
import com.example.app.RuntimeData;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.MenuUI;
import com.example.app.control.UserControl;
import com.example.app.models.ProjectFilter;

/**
 * Represents the main dashboard for applicants.
 *
 * <p>This class provides a menu interface for applicants to:
 * <ul>
 *   <li>View available projects</li>
 *   <li>View their applied project</li>
 *   <li>View their enquiries</li>
 *   <li>Logout from the system</li>
 * </ul>
 *
 * @see ApplicantProjectListUI
 * @see ApplicantAppliedProjectUI
 * @see ApplicantEnquiryUI
 */
public class ApplicantUI {
    private final ApplicantControl ctrl;
    private final MenuUI menu;
    private final ProjectFilter projectFilter = new ProjectFilter();

    public ApplicantUI() {
        Applicant me = (Applicant) RuntimeData.getCurrentUser();
        this.ctrl = new ApplicantControl(me);
        this.menu = buildMenu();
    }

    public void run() {
        menu.run();
    }

    private MenuUI buildMenu() {
        MenuUI m = new MenuUI(Helper.toHeader("Applicant Dashboard"));
        m.addOption("View Projects", () -> new ApplicantProjectListUI(ctrl, projectFilter).run());
        m.addOption("View Applied Project", () -> new ApplicantAppliedProjectUI(ctrl).run());
        m.addOption("View Enquiries", () -> new ApplicantEnquiryUI(ctrl).run());
        m.addOption("Logout", () -> {
            UserControl.logout();
            Readers.readEnter();
            m.exit();
        });
        return m;
    }
}
