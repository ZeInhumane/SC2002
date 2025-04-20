package com.example.app;

import com.example.app.models.Applicant;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.User;

public class RuntimeData {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Applicant getCurrentApplicant() {
        if (currentUser instanceof Applicant) {
            return (Applicant) currentUser;
        }
        throw new IllegalStateException("Current user is not an applicant");
    }

    public static Officer getCurrentOfficer() {
        if (currentUser instanceof Officer) {
            return (Officer) currentUser;
        }
        throw new IllegalStateException("Current user is not an officer");
    }

    public static Manager getCurrentManager() {
        if (currentUser instanceof Manager) {
            return (Manager) currentUser;
        }
        throw new IllegalStateException("Current user is not a manager");
    }

    public static void setCurrentUser(User currentUser) {
        RuntimeData.currentUser = currentUser;
    }
}
