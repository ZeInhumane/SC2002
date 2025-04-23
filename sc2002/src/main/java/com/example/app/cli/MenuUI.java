package com.example.app.cli;

import java.util.LinkedHashMap;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

/**
 * Generic numbered menu loop. Call exit() to break out of run().
 */
public class MenuUI {
    private final String title;
    private final LinkedHashMap<Integer, MenuOption> options = new LinkedHashMap<>();
    private boolean exit = false;

    private static class MenuOption {
        final String label;
        final Runnable action;
        MenuOption(String label, Runnable action) {
            this.label = label;
            this.action = action;
        }
    }

    public MenuUI(String title) {
        this.title = title;
    }

    /** Add a numbered menu option. */
    public void addOption(String label, Runnable action) {
        options.put(options.size() + 1, new MenuOption(label, action));
    }

    /** Exit the menu loop. */
    public void exit() {
        this.exit = true;
    }

    /** Run the menu loop until exit() is called. */
    public void run() {
        exit = false;
        while (!exit) {
            System.out.println(title + "\n");
            options.forEach((num, opt) ->
                System.out.printf("%d. %s%n", num, opt.label)
            );
            int choice = Readers.readIntRange(1, options.size());
            Helper.wipeScreen();
            options.get(choice).action.run();
        }
    }
}
