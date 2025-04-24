package com.example.app;

import com.example.app.cli.LoginUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Reset data for demo/testing? (Y/N): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("Y")) {
            DataInitializer.initializeData();
            System.out.println("Data has been reset.");
        }

        new LoginUI().run();
    }
}