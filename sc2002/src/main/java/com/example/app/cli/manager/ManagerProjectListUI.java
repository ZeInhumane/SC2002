package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Project;
import com.example.app.models.ProjectFilter;
import com.example.app.enums.FlatType;
import com.example.app.cli.utils.*;
import com.example.app.cli.common.PaginatedUI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerProjectListUI {
    private final ManagerControl ctrl;
    private final ProjectFilter filter;

    public ManagerProjectListUI(ManagerControl ctrl, ProjectFilter filter) {
        this.ctrl = ctrl;
        this.filter = filter;
    }

    public void run() {
        while (true) {
            Helper.wipeScreen();
            System.out.println("1. Set Filters");
            System.out.println("2. View Projects");
            System.out.println("3. View Current Filter");
            System.out.println("4. Back");
            int choice = Readers.readIntRange(1, 4);
            switch (choice) {
                case 1 -> setFilters();
                case 2 -> showProjects();
                case 3 -> showCurrentFilter();
                case 4 -> { return; }
            }
        }
    }

    private void setFilters() {
        String neighborhood = Readers.readStringAcceptEmpty("Enter neighborhood/location (leave blank for any):");
        filter.setNeighborhood(neighborhood == null || neighborhood.trim().isEmpty() ? null : neighborhood.trim());
        System.out.println("Select Flat Type: 0. Any  2. 2-Room  3. 3-Room");
        String ftInput = Readers.readStringAcceptEmpty("");
        int ftChoice = 0;
        try {
            ftChoice = ftInput == null || ftInput.trim().isEmpty() ? 0 : Integer.parseInt(ftInput.trim());
        } catch (NumberFormatException e) {
            ftChoice = 0;
        }
        switch (ftChoice) {
            case 2 -> filter.setFlatType(FlatType._2ROOM);
            case 3 -> filter.setFlatType(FlatType._3ROOM);
            default -> filter.setFlatType(null);
        }
        String projectName = Readers.readStringAcceptEmpty("Enter project name (leave blank for any):");
        filter.setProjectName(projectName == null || projectName.trim().isEmpty() ? null : projectName.trim());
    }

    private void showCurrentFilter() {
        System.out.println("Current Filter:");
        System.out.println("  Neighborhood: " + (filter.getNeighborhood() == null ? "Any" : filter.getNeighborhood()));
        System.out.println("  Flat Type: " + (filter.getFlatType() == null ? "Any" : filter.getFlatType()));
        System.out.println("  Project Name: " + (filter.getProjectName() == null ? "Any" : filter.getProjectName()));
        Readers.readEnter();
    }

    private void showProjects() {
        new PaginatedUI<>(Helper.toHeader("All Projects"), this::getFilteredProjects, project -> {return;}, 5, "No projects found.").run();
    }

    private List<Project> getFilteredProjects() {
        List<Project> projects = ctrl.getAllProjects();
        return projects.stream()
            .filter(p -> filter.getNeighborhood() == null || p.getNeighborhood().equalsIgnoreCase(filter.getNeighborhood()))
            .filter(p -> filter.getFlatType() == null || (p.getFlats() != null && p.getFlats().containsKey(filter.getFlatType())))
            .filter(p -> filter.getProjectName() == null || p.getProjectName().toLowerCase().contains(filter.getProjectName().toLowerCase()))
            .sorted(Comparator.comparing(Project::getProjectName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    private void handleProjectSelection(Project project) {
        // No actions for ManagerProjectListUI, but if you add any with try/catch, use only e.getMessage()
    }
}
