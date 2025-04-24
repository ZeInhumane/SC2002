package com.example.app.control;

import com.example.app.service.UserService;
import com.example.app.service.impl.UserServiceImpl;
import com.example.app.RuntimeData;
import com.example.app.models.User;

public class UserControl {
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    // Default constructor wiring for production
    public UserControl() {
        this(new UserServiceImpl());
    }

    public boolean login(String nric, String password) {
        nric = nric.trim().toUpperCase(); // Normalize NRIC input
        password = password.trim(); // Normalize password input

        // Validate NRIC format
        if (!nric.matches("^[ST]\\d{7}[A-Z]$")) {
            System.out.println(
                    "Invalid NRIC format. NRIC should start with 'S' or 'T' followed by 7 digits and another letter.");
            return false;
        }

        try {
            User user = userService.login(nric, password);
            if (user != null) {
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

    public void changePassword(User user, String newPassword) throws Exception {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        newPassword = newPassword.trim(); // Normalize new password input

        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty.");
        }

        userService.changePassword(user, newPassword);
    }

    public static void logout() {
        RuntimeData.setCurrentUser(null);
        System.out.println("You have been logged out successfully.");
    }
}
