package com.gmail.visualbukkit.reflection;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayClassInfo extends ClassInfo {

    private static final Pattern NAME_PATTERN = Pattern.compile("(.+?)(\\[])+");
    private final ClassInfo type;
    private final String arrayBrackets;

    protected ArrayClassInfo(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (matcher.matches()) {
            type = of(matcher.group(1));
            arrayBrackets = matcher.group(2);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String getName() {
        return type.getName() + arrayBrackets;
    }

    @Override
    public String getSimpleName() {
        return type.getSimpleName() + arrayBrackets;
    }

    @Override
    public String getPackage() {
        return type.getPackage();
    }

    @Override
    public Set<FieldInfo> getFields() {
        return Collections.emptySet();
    }

    @Override
    public Set<ConstructorInfo> getConstructors() {
        return Collections.emptySet();
    }

    @Override
    public Set<MethodInfo> getMethods() {
        return Collections.emptySet();
    }
}
