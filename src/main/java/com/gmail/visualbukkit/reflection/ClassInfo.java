package com.gmail.visualbukkit.reflection;

import com.google.common.primitives.Primitives;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ClassInfo implements Comparable<ClassInfo> {

    public abstract String getName();

    public abstract String getSimpleName();

    public abstract String getPackage();

    public abstract Set<MethodInfo> getMethods();

    public Set<MethodInfo> getMethods(Predicate<MethodInfo> filter) {
        return getMethods().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    private boolean isPrimitive() {
        for (Class<?> clazz : Primitives.allPrimitiveTypes()) {
            if (clazz.getCanonicalName().equals(getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrimitiveNumber() {
        return isPrimitive() && !getName().equals("boolean") && !getName().equals("char");
    }

    @Override
    public int compareTo(ClassInfo o) {
        return getSimpleName().compareTo(o.getSimpleName());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ClassInfo other && getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getSimpleName(), getPackage());
    }

    public static String convert(ClassInfo from, ClassInfo to, String java) {
        if (from.equals(to)) {
            return java;
        }
        if (to.getName().equals("java.lang.String")) {
            return "String.valueOf(" + java + ")";
        }
        if (from.isPrimitiveNumber() && to.isPrimitiveNumber()) {
            return String.format("((%s) %s)", to.getName(), java);
        }
        if (to.isPrimitive()) {
            return String.format("PluginMain.resolve_%s(%s)", to.getName(), java);
        }
        return String.format("PluginMain.resolve_object(%s, %s.class)", java, to.getName());
    }

    public static ClassInfo of(String name) {
        return name.endsWith("[]") ? new ArrayClassInfo(name) : ClassRegistry.getClass(name).orElse(new UnknownClassInfo(name));
    }

    public static ClassInfo of(Class<?> clazz) {
        return of(clazz.getCanonicalName());
    }
}
