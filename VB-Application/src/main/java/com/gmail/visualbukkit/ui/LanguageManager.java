package com.gmail.visualbukkit.ui;

import java.util.ResourceBundle;

public class LanguageManager {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.GUI");

    public static String get(String key) {
        return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : key;
    }
}
