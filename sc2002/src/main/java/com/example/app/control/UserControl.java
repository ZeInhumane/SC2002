package com.example.app.control;

import com.example.app.models.User;
import com.example.app.service.impl.UserServiceImpl;

public class UserControl {
    public static boolean login(String nric, String password) {
        nric = nric.trim().toUpperCase(); // Normalize NRIC input
        password = password.trim(); // Normalize password input

        // Validate NRIC format
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            System.out.println("Invalid NRIC format. NRIC should start with 'S' or 'T' followed by 7 digits and another letter.");
            return false;
        }

        try {
            User user = UserServiceImpl.getInstance().login(nric, password);
            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return true;
            } else {
                System.out.println("Login failed. Please check your NRIC and password.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage() + ". Please try again.");
            return false;
        }
    }
}
