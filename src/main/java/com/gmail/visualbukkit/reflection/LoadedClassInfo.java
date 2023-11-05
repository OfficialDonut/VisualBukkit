package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

public class LoadedClassInfo extends ClassInfo {

    private final Class<?> clazz;
    private Set<MethodInfo> methods;

    protected LoadedClassInfo(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getCanonicalName();
    }

    @Override
    public String getSimpleName() {
        return clazz.getSimpleName();
    }

    @Override
    public String getPackage() {
        return clazz.getPackageName();
    }

    @Override
    public Set<MethodInfo> getMethods() {
        if (methods == null) {
            methods = new TreeSet<>();
            for (Method method : clazz.getMethods()) {
                methods.add(new LoadedMethodInfo(method));
            }
        }
        return methods;
    }
}
