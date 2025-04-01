package com.example.app.utils;

import java.util.Scanner;

public class InputReader {
    static private final Scanner scanner = new Scanner(System.in);

    static public String readString() {
        return scanner.nextLine();
    }

    static public int readInt() {
        return scanner.nextInt();
    }

    static public double readDouble() {
        return scanner.nextDouble();
    }
}
