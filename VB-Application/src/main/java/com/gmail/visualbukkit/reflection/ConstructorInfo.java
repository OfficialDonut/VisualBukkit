package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.ui.PopOverSelectable;

public abstract class ConstructorInfo implements Parameterizable, PopOverSelectable, Comparable<ConstructorInfo> {

    private final ClassInfo clazz;

    public ConstructorInfo(ClassInfo clazz) {
        this.clazz = clazz;
    }

    public String getSignature() {
        return String.format("<init>(%s)", getParameterString(p -> p.getType().getName()));
    }

    @Override
    public String getPinID() {
        return getSignature();
    }

    @Override
    public int compareTo(ConstructorInfo o) {
        return getSignature().compareTo(o.getSignature());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ConstructorInfo other && getSignature().equals(other.getSignature());
    }

    @Override
    public int hashCode() {
        return getSignature().hashCode();
    }

    @Override
    public String toString() {
        return String.format("new %s(%s)", clazz.getSimpleName(), getParameterString(p -> p.getType().getSimpleName()));
    }
}
