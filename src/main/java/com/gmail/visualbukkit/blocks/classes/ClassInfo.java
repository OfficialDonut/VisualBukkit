package com.gmail.visualbukkit.blocks.classes;

import com.google.common.primitives.Primitives;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassInfo implements Comparable<ClassInfo> {

    private final JSONObject json;
    private Set<MethodInfo> methods;

    protected ClassInfo(JSONObject json) {
        this.json = json;
    }

    protected ClassInfo(String name) {
        json = new JSONObject();
        json.put("name", name);
        json.put("package", "");
        if (name.contains(".")) {
            json.put("simple-name", name.substring(name.lastIndexOf(".") + 1));
        } else {
            json.put("simple-name", name);
        }
    }

    public String getName() {
        return json.getString("name");
    }

    public String getSimpleName() {
        return json.getString("simple-name");
    }

    public String getPackage() {
        return json.getString("package");
    }

    public Set<MethodInfo> getMethods() {
        if (methods == null) {
            methods = new TreeSet<>();
            JSONArray methodsJson = json.optJSONArray("methods");
            if (methodsJson != null) {
                for (Object o : methodsJson) {
                    methods.add(new MethodInfo((JSONObject) o));
                }
            }
        }
        return methods;
    }

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
        return name.endsWith("[]") ? new ArrayClassInfo(name) : ClassRegistry.getClass(name).orElse(new ClassInfo(name));
    }

    public static ClassInfo of(Class<?> clazz) {
        return of(clazz.getCanonicalName());
    }
}
