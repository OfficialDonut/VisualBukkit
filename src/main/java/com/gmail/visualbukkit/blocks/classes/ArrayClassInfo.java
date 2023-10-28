package com.gmail.visualbukkit.blocks.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayClassInfo extends ClassInfo {

    private static final Pattern NAME_PATTERN = Pattern.compile("(.+?)(\\[])+");
    private final ClassInfo type;
    private final String arrayBrackets;

    protected ArrayClassInfo(String name) {
        super(name);
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (matcher.matches()) {
            type = of(matcher.group(1));
            arrayBrackets = matcher.group(2);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String getSimpleName() {
        return type.getSimpleName() + arrayBrackets;
    }

    @Override
    public String getPackage() {
        return type.getPackage();
    }
}
