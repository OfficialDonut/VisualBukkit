package com.gmail.visualbukkit.reflection;

import java.util.Collections;
import java.util.Set;

public class UnknownClassInfo extends ClassInfo {

    private final String name;
    private final String simpleName;
    private final String packageName;

    protected UnknownClassInfo(String name) {
        this.name = name;
        int i = name.lastIndexOf(".");
        if (i != -1) {
            simpleName = name.substring(i + 1);
            packageName = name.substring(0, i);
        } else {
            simpleName = name;
            packageName = "";
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public String getPackage() {
        return packageName;
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
