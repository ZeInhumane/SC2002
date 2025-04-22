package com.example.app.cli.utils;

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
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
