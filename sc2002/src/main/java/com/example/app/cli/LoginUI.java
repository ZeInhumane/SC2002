package com.example.app.cli;

import com.example.app.cli.utils.*;
import com.example.app.control.UserControl;
import com.example.app.RuntimeData;

import com.example.app.cli.applicant.ApplicantUI;
import com.example.app.cli.common.MenuUI;
import com.example.app.cli.officer.OfficerUI;
import com.example.app.cli.manager.ManagerUI;

public class LoginUI {
    private final MenuUI menu;
    private final UserControl userControl;

    public LoginUI() {
        this.userControl = new UserControl();
        this.menu = buildMenu();
    }

    public void run() {
        Helper.wipeScreen();
        menu.run();
    }

    private MenuUI buildMenu() {
        MenuUI m = new MenuUI(Helper.toHeader("Main Menu"));
        m.addOption("Login", this::login);
        m.addOption("Change Password", this::changePassword);
        m.addOption("Exit", m::exit);
        return m;
    }

    private void login() {
        String nric = Readers.readString("Enter your NRIC: ");
        String password = Readers.readPassword();
        Helper.wipeScreen();
        if (userControl.login(nric, password)) {
            System.out.println("Login successful. Welcome, " + RuntimeData.getCurrentUser().getName() + "!");
            Readers.readEnter();
            switch (RuntimeData.getCurrentUser().getRole()) {
            case APPLICANT:
                new ApplicantUI().run();
                break;
            case OFFICER:
                new OfficerUI().run();
                break;
            case MANAGER:
                new ManagerUI().run();
                break;
            default:
                System.out.println("Unknown role. Please contact support.");
                break;
            }
        } else
            Readers.readEnter();
    }

    private void changePassword() {
        String changeNric = Readers.readString("Enter your NRIC: ");
        String currentPassword = Readers.readPassword("Enter your current password (case-sensitive): ");
        if (userControl.login(changeNric, currentPassword)) {
            String newPassword = Readers.readPassword("Enter your new password (case-sensitive): ");
            try {
                userControl.changePassword(RuntimeData.getCurrentUser(), newPassword);
                System.out.println("Password changed successfully.");
                UserControl.logout();
            } catch (Exception e) {
                System.out.println("Error changing password: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid NRIC or password. Please try again.");
        }
        Readers.readEnter();
    }
}