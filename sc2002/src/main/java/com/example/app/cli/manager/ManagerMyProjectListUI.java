package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.models.Project;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.common.PaginatedUI;

/**
 * Displays a paginated list of projects assigned to the manager.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>View their assigned projects</li>
 *   <li>Select a project for further management</li>
 * </ul>
 *
 * @see ManagerProjectManagementUI
 */
public class ManagerMyProjectListUI {
    private final ManagerControl ctrl;
    private final PaginatedUI<Project> paginator;

    public ManagerMyProjectListUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
        this.paginator = buildPaginator();
    }

    public void run() {
        paginator.run();
    }

    private PaginatedUI<Project> buildPaginator() {
        return new PaginatedUI<>(Helper.toHeader("My Projects"), ctrl::getMyProjects, this::handleProjectSelection, 5,
                "No projects found.");
    }

    private void handleProjectSelection(Project project) {
        new ManagerProjectManagementUI(ctrl, project).run();
    }
}
