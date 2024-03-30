package com.gmail.visualbukkit.reflection;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import java.util.HashSet;
import java.util.Set;

public class CtClassInfo extends ClassInfo {

    private final CtClass clazz;
    private Set<FieldInfo> fields;
    private Set<ConstructorInfo> constructors;
    private Set<MethodInfo> methods;

    protected CtClassInfo(CtClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getName().replace("$", ".");
    }

    @Override
    public String getSimpleName() {
        return clazz.getSimpleName().replaceAll(".+\\$", "");
    }

    @Override
    public String getPackage() {
        return clazz.getPackageName();
    }

    @Override
    public Set<FieldInfo> getFields() {
        if (fields == null) {
            fields = new HashSet<>();
            for (CtField field : clazz.getFields()) {
                fields.add(new CtFieldInfo(field));
            }
        }
        return fields;
    }

    @Override
    public Set<ConstructorInfo> getConstructors() {
        if (constructors == null) {
            constructors = new HashSet<>();
            for (CtConstructor constructor : clazz.getConstructors()) {
                constructors.add(new CtConstructorInfo(constructor));
            }
        }
        return constructors;
    }

    @Override
    public Set<MethodInfo> getMethods() {
        if (methods == null) {
            methods = new HashSet<>();
            for (CtMethod method : clazz.getMethods()) {
                methods.add(new CtMethodInfo(method));
            }
        }
        return methods;
    }
}
