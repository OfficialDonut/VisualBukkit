package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class LoadedClassInfo extends ClassInfo {

    private final Class<?> clazz;
    private Set<FieldInfo> fields;
    private Set<ConstructorInfo> constructors;
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
    public Set<FieldInfo> getFields() {
        if (fields == null) {
            fields = new HashSet<>();
            for (Field field : clazz.getFields()) {
                fields.add(new LoadedFieldInfo(field));
            }
        }
        return fields;
    }

    @Override
    public Set<ConstructorInfo> getConstructors() {
        if (constructors == null) {
            constructors = new HashSet<>();
            for (Constructor<?> constructor : clazz.getConstructors()) {
                constructors.add(new LoadedConstructorInfo(constructor));
            }
        }
        return constructors;
    }

    @Override
    public Set<MethodInfo> getMethods() {
        if (methods == null) {
            methods = new HashSet<>();
            for (Method method : clazz.getMethods()) {
                methods.add(new LoadedMethodInfo(method));
            }
        }
        return methods;
    }
}
