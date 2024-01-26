package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.ui.PopOverSelectable;
import com.google.common.primitives.Primitives;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ClassInfo implements PopOverSelectable, Comparable<ClassInfo> {

    public abstract String getName();

    public abstract String getSimpleName();

    public abstract String getPackage();

    public abstract Set<FieldInfo> getFields();

    public abstract Set<ConstructorInfo> getConstructors();

    public abstract Set<MethodInfo> getMethods();

    public Set<FieldInfo> getFields(Predicate<FieldInfo> filter) {
        return getFields().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<ConstructorInfo> getConstructors(Predicate<ConstructorInfo> filter) {
        return getConstructors().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<MethodInfo> getMethods(Predicate<MethodInfo> filter) {
        return getMethods().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    public Optional<FieldInfo> getField(String name) {
        for (FieldInfo field : getFields()) {
            if (field.getName().equals(name)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    public Optional<ConstructorInfo> getConstructor(ClassInfo... parameterTypes) {
        for (ConstructorInfo constructor : getConstructors()) {
            if (constructor.checkParameterTypes(parameterTypes)) {
                return Optional.of(constructor);
            }
        }
        return Optional.empty();
    }

    public Optional<MethodInfo> getMethod(String name, ClassInfo... parameterTypes) {
        for (MethodInfo method : getMethods()) {
            if (method.getName().equals(name) && method.checkParameterTypes(parameterTypes)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

    @Override
    public String getPinID() {
        return getName();
    }

    @Override
    public Node[] getDisplayNodes() {
        Label nameLabel = new Label(getSimpleName());
        Label packageLabel = new Label("(" + getPackage() + ")");
        nameLabel.getStyleClass().add("class-name-label");
        packageLabel.getStyleClass().add("class-package-label");
        return new Node[]{nameLabel, packageLabel};
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
        if (from == null || to == null || from.equals(to) || from == OBJECT_OR_PRIMITIVE || to == OBJECT_OR_PRIMITIVE || (to.getName().equals("java.lang.Object") && !from.isPrimitive())) {
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

    public static final ClassInfo OBJECT_OR_PRIMITIVE = new ClassInfo() {
        @Override
        public String getName() {
            return "anything";
        }

        @Override
        public String getSimpleName() {
            return "anything";
        }

        @Override
        public String getPackage() {
            return "";
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
    };
}
