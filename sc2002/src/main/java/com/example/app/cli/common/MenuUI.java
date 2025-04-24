package com.example.app.cli.common;

import java.util.LinkedHashMap;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

/**
 * A utility class for creating and managing a numbered menu interface.
 *
 * This class provides a simple way to display a menu with options and execute corresponding actions.
 */
public class MenuUI {
    /**
     * The content to display at the top of the menu.
     */
    private final String content;
    /**
     * A map of menu options, where the key is the option number and the value is the corresponding action.
     */
    private final LinkedHashMap<Integer, MenuOption> options = new LinkedHashMap<>();
    /**
     * A flag to indicate whether to exit the menu loop.
     */
    private boolean exit = false;

    /**
     * A private inner class representing a menu option.
     */
    private static class MenuOption {
        /**
         * The label to display for this option.
         */
        final String label;
        /**
         * The action to perform when this option is selected.
         */
        final Runnable action;

        MenuOption(String label, Runnable action) {
            this.label = label;
            this.action = action;
        }
    }

    public MenuUI(String content) {
        this.content = content;
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
            Helper.wipeScreen();
            System.out.println(content + "\n");
            options.forEach((num, opt) -> System.out.printf("%d. %s%n", num, opt.label));
            int choice = Readers.readIntRange(1, options.size());
            options.get(choice).action.run();
        }
    }
}
