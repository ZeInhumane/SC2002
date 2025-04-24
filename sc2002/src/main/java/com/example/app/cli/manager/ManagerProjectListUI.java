package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Project;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.common.PaginatedUI;

public class ManagerProjectListUI {
    private final ManagerControl ctrl;
    private final PaginatedUI<Project> paginator;

    public ManagerProjectListUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Project> buildPaginator() {
        return new PaginatedUI<>(
            Helper.toHeader("All Projects"),
            ctrl::getAllProjects,
            project -> {}, // No action on selection
            5,
            "No projects found."
        );
    }
}
