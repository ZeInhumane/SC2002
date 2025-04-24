package com.example.app.cli.utils;

import java.util.Scanner;

public class Readers {
    public static final Scanner sc = new Scanner(System.in);

    public static String readString() {
        String input = sc.nextLine();
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
        }
        return input;
    }

    public static String readString(String prompt) {
        System.out.println(prompt);
        String input = sc.nextLine();
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Please try again.");
        }
        return input;
    }

    public static void readEnter() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        Helper.wipeScreen();
    }

    public static String readPassword() {
        String password;
        while (true) {
            if (System.console() == null) {
                System.out.println("Enter password (case-sensitive): ");
                password = sc.nextLine();
            } else {
                password = new String(System.console().readPassword("Enter password (case-sensitive): "));
            }

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return password;
    }

    public static String readPassword(String prompt) {
        String password;
        while (true) {
            System.out.println(prompt);
            if (System.console() == null) {
                password = sc.nextLine();
            } else {
                password = new String(System.console().readPassword());
            }

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return password;
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static int readIntRange(int min, int max) throws IllegalArgumentException {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        while (true) {
            System.out.println("Enter your choice (" + min + " - " + max + "): ");
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input < min || input > max) {
                    System.out.println("Input out of range. Please enter a value between " + min + " and " + max + ".");
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
