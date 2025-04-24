package com.example.app.cli.manager;

import com.example.app.control.ManagerControl;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Provides an interface for creating new projects.
 *
 * <p>This class allows managers to:
 * <ul>
 *   <li>Specify project details such as name, neighborhood, and dates</li>
 *   <li>Set constraints like officer limits and flat types</li>
 *   <li>Define eligible groups for the project</li>
 * </ul>
 */
public class ManagerCreateProjectUI {
    private final ManagerControl ctrl;

    public ManagerCreateProjectUI(ManagerControl ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        try {
            System.out.println(Helper.toHeader("Create New Project"));
            String name = Readers.readString("Enter project name:").trim();
            if (name.isEmpty()) {
                System.out.println("Project name cannot be empty.");
                Readers.readEnter();
                return;
            }
            String neighborhood = Readers.readString("Enter neighborhood:").trim();
            if (neighborhood.isEmpty()) {
                System.out.println("Neighborhood cannot be empty.");
                Readers.readEnter();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date openDate = null, closeDate = null;
            while (true) {
                String openDateStr = Readers.readString("Enter application open date (yyyy-MM-dd):").trim();
                try {
                    openDate = sdf.parse(openDateStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                }
            }
            while (true) {
                String closeDateStr = Readers.readString("Enter application close date (yyyy-MM-dd):").trim();
                try {
                    closeDate = sdf.parse(closeDateStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                }
            }
            if (!openDate.before(closeDate)) {
                System.out.println("Open date must be before close date.");
                Readers.readEnter();
                return;
            }
            if (!openDate.after(new Date())) {
                System.out.println("Open date must be after today.");
                Readers.readEnter();
                return;
            }
            int officerLimit = Readers.readInt("Enter officer limit (max 10):");
            if (officerLimit < 1 || officerLimit > 10) {
                System.out.println("Officer limit must be between 1 and 10.");
                Readers.readEnter();
                return;
            }
            int twoRoom = Readers.readInt("Enter number of 2-Room units:");
            int threeRoom = Readers.readInt("Enter number of 3-Room units:");
            if (twoRoom < 0 || threeRoom < 0) {
                System.out.println("Number of units cannot be negative.");
                Readers.readEnter();
                return;
            }
            if (twoRoom == 0 && threeRoom == 0) {
                System.out.println("At least one flat unit must be provided.");
                Readers.readEnter();
                return;
            }
            Map<FlatType, Integer> flats = new HashMap<>();
            flats.put(FlatType._2ROOM, twoRoom);
            flats.put(FlatType._3ROOM, threeRoom);
            System.out.println("Select eligible groups (comma separated):");
            System.out.println("1. MARRIED\n2. SINGLE");
            Set<MaritalStatus> groups = new HashSet<>();
            while (groups.isEmpty()) {
                String groupInput = Readers.readString("Enter choices (e.g. 1,2):");
                for (String s : groupInput.split(",")) {
                    s = s.trim();
                    if (s.equals("1"))
                        groups.add(MaritalStatus.MARRIED);
                    if (s.equals("2"))
                        groups.add(MaritalStatus.SINGLE);
                }
                if (groups.isEmpty()) {
                    System.out.println("At least one group must be selected.");
                }
            }
            boolean visibility = true; // Default to visible on creation
            ctrl.createProject(name, openDate, closeDate, neighborhood, visibility, officerLimit, new HashSet<>(),
                    groups, flats);
            System.out.println("Project created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating project: " + e.getMessage());
        }
        Readers.readEnter();
    }
}
