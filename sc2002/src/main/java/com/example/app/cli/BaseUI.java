package com.example.app.cli;

public interface BaseUI {
    default public void printMenu() {} // print the menu actions
    public void run(); // takes in input and calls controllers/services as appropriate
}
