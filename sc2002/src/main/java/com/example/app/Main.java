package com.example.app;

import com.example.app.cli.LoginUI;
import com.example.app.service.UserService;
import com.example.app.service.impl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        new LoginUI().run();
    }
}
