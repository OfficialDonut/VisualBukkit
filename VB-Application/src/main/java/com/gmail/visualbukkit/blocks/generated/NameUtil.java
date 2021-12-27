package com.gmail.visualbukkit.blocks.generated;

public class NameUtil {

    protected static String formatClassName(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return str.substring(i);
            }
        }
        return str;
    }

    protected static String formatLowerCamelCase(String str) {
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            builder.append(c);
            if (i + 1 < str.length() && Character.isUpperCase(str.charAt(i + 1)) && (Character.isLowerCase(c) || (i + 2 < str.length() && Character.isLowerCase(str.charAt(i + 2))))) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }
}
