package com.example.app.cli;

import com.example.app.cli.utils.*;
import com.example.app.control.UserControl;
import com.example.app.RuntimeData;

public class LoginUI implements BaseUI {
    public void printMenu() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
    }

    @Override
    public void run() {
        Helper.wipeScreen();
        while (true) {
            printMenu();

            int choice = Readers.readIntRange(1, 2);

            switch (choice) {
                case 1:
                    String nric = Readers.readString("Enter your NRIC: ");
                    String password = Readers.readPassword();
                    Helper.wipeScreen();
                    if (UserControl.login(nric, password)) {
                        switch (RuntimeData.getCurrentUser().getRole()) {
                            case APPLICANT:
                                new ApplicantUI().run();
                                break;
                            case OFFICER:
                                // new OfficerUI().run();
                                break;
                            case MANAGER:
                                // new ManagerUI().run();
                                break;
                            default:
                                System.out.println("Unknown role. Please contact support.");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;    
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
    }
}