package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.ApplicantBookingReportRow;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
import java.util.List;

/**
 * Generates and displays applicant booking reports for managers.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>Filter reports by marital status, flat type, project name, and age range</li>
 *   <li>Reset filters</li>
 *   <li>View the filtered report</li>
 * </ul>
 */
public class ManagerReportUI {
    private final ManagerControl ctrl;
    private MaritalStatus maritalStatusFilter = null;
    private FlatType flatTypeFilter = null;
    private String projectNameFilter = null;
    private Integer minAgeFilter = null;
    private Integer maxAgeFilter = null;

    public ManagerReportUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        while (true) {
            Helper.wipeScreen();
            System.out.println(Helper.toHeader("Applicant Booking Report"));
            displayCurrentFilters();
            displayReport();
            System.out.println("\nFilter Options:");
            System.out.println("1. Filter by Marital Status");
            System.out.println("2. Filter by Flat Type");
            System.out.println("3. Filter by Project Name");
            System.out.println("4. Filter by Age Range");
            System.out.println("5. Reset Filters");
            System.out.println("6. Back");
            int choice = Readers.readIntRange(1, 6);
            switch (choice) {
            case 1 -> filterMaritalStatus();
            case 2 -> filterFlatType();
            case 3 -> filterProjectName();
            case 4 -> filterAgeRange();
            case 5 -> resetFilters();
            case 6 -> {
                return;
            }
            }
        }
    }

    private void displayCurrentFilters() {
        System.out.println("Current Filters:");
        System.out.println("  Marital Status: " + (maritalStatusFilter == null ? "All" : maritalStatusFilter));
        System.out.println("  Flat Type: " + (flatTypeFilter == null ? "All" : flatTypeFilter));
        System.out.println("  Project Name: "
                + (projectNameFilter == null || projectNameFilter.isEmpty() ? "All" : projectNameFilter));
        System.out.println("  Age Range: "
                + (minAgeFilter == null && maxAgeFilter == null ? "All" : (minAgeFilter == null ? "<= " + maxAgeFilter
                        : maxAgeFilter == null ? ">= " + minAgeFilter : minAgeFilter + " - " + maxAgeFilter)));
    }

    private void displayReport() {
        try {
            List<ApplicantBookingReportRow> rows = ctrl.getBookedApplicationsReport(maritalStatusFilter, flatTypeFilter,
                    projectNameFilter, minAgeFilter, maxAgeFilter);
            if (rows.isEmpty()) {
                System.out.println("\nNo results found for the current filters.");
            } else {
                System.out.println("\nApplicant Name         | Age | Marital  | Project Name         | Flat   ");
                System.out.println("----------------------|-----|----------|---------------------|--------");
                for (ApplicantBookingReportRow row : rows) {
                    System.out.println(row);
                }
                System.out.println("\nTotal: " + rows.size() + " result(s).");
            }
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void filterMaritalStatus() {
        System.out.println("Select Marital Status:");
        System.out.println("1. All");
        System.out.println("2. Married");
        System.out.println("3. Single");
        int choice = Readers.readIntRange(1, 3);
        maritalStatusFilter = switch (choice) {
        case 2 -> MaritalStatus.MARRIED;
        case 3 -> MaritalStatus.SINGLE;
        default -> null;
        };
    }

    private void filterFlatType() {
        System.out.println("Select Flat Type:");
        System.out.println("1. All");
        System.out.println("2. 2-Room");
        System.out.println("3. 3-Room");
        int choice = Readers.readIntRange(1, 3);
        flatTypeFilter = switch (choice) {
        case 2 -> FlatType._2ROOM;
        case 3 -> FlatType._3ROOM;
        default -> null;
        };
    }

    private void filterProjectName() {
        String input = Readers.readString("Enter project name (substring, leave blank for all):").trim();
        projectNameFilter = input.isEmpty() ? null : input;
    }

    private void filterAgeRange() {
        String minStr = Readers.readString("Enter minimum age (leave blank for no minimum):").trim();
        String maxStr = Readers.readString("Enter maximum age (leave blank for no maximum):").trim();
        minAgeFilter = minStr.isEmpty() ? null : parseIntOrNull(minStr);
        maxAgeFilter = maxStr.isEmpty() ? null : parseIntOrNull(maxStr);
        if (minAgeFilter != null && maxAgeFilter != null && minAgeFilter > maxAgeFilter) {
            System.out.println("Minimum age cannot be greater than maximum age. Resetting both.");
            minAgeFilter = null;
            maxAgeFilter = null;
            Readers.readEnter();
        }
    }

    private void resetFilters() {
        maritalStatusFilter = null;
        flatTypeFilter = null;
        projectNameFilter = null;
        minAgeFilter = null;
        maxAgeFilter = null;
    }

    private Integer parseIntOrNull(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
