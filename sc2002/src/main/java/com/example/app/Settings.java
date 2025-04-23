package com.example.app;

import com.example.app.enums.RunMode;

public class Settings {
    public static final RunMode runMode = RunMode.DEVELOPMENT;
    public static final String DB_PATH = "src/main/resources/db/" + runMode.toString().toLowerCase() + "/";
}
