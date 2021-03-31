package com.gmail.visualbukkit.blocks;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TypeHandler {

    private static Set<ResourceBundle> classNameResourceBundles = new HashSet<>();

    public static void registerClassNames(ResourceBundle resourceBundle) {
        classNameResourceBundles.add(resourceBundle);
    }

    public static String getDisplayClassName(String key) {
        for (ResourceBundle resourceBundle : classNameResourceBundles) {
            if (resourceBundle.containsKey(key)) {
                return resourceBundle.getString(key);
            }
        }
        return null;
    }
}
