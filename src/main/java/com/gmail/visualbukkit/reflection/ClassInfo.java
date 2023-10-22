package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record ClassInfo(Class<?> clazz) implements Comparable<ClassInfo> {

    public Set<MethodInfo> getMethods() {
        Set<MethodInfo> methods = new TreeSet<>();
        for (Method method : clazz.getMethods()) {
            methods.add(new MethodInfo(method));
        }
        return methods;
    }

    public Set<MethodInfo> getMethods(Predicate<MethodInfo> filter) {
        return Arrays.stream(clazz.getMethods()).map(MethodInfo::new).filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public int compareTo(ClassInfo o) {
        return clazz.getSimpleName().compareTo(o.clazz.getSimpleName());
    }

    @Override
    public String toString() {
        return toString(clazz, true);
    }

    public static String toString(Class<?> clazz, boolean displayPackage) {
        return displayPackage ? String.format("%s (%s)", clazz.getSimpleName(), clazz.getPackageName()) : clazz.getSimpleName();
    }

    public static String convert(Class<?> from, Class<?> to, String java) {
        if (to.isAssignableFrom(from)) {
            return java;
        }
        if (to == String.class) {
            return "String.valueOf(" + java + ")";
        }
        if (isPrimitiveNumber(from) && isPrimitiveNumber(to)) {
            return String.format("((%s) %s)", to.getCanonicalName(), java);
        }
        if (to.isPrimitive()) {
            return String.format("PluginMain.resolve_%s(%s)", to.getCanonicalName(), java);
        }
        return String.format("PluginMain.resolve_object(%s, %s.class)", java, to.getCanonicalName());
    }

    private static boolean isPrimitiveNumber(Class<?> clazz) {
        return clazz.isPrimitive() && clazz != boolean.class && clazz != char.class;
    }
}
