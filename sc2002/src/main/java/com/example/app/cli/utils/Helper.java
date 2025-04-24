package com.example.app.cli.utils;

/**
 * A utility class for common helper methods used in the CLI.
 */
public class Helper {
    public static void wipeScreen() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                pb.inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.print("\033[3J\033[2J\033[H");
            System.out.flush();
        }
    }

    public static String toHeader(String str) {
        return "== " + str + " ==";
    }
}
