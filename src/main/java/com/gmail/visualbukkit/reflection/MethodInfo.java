package com.gmail.visualbukkit.reflection;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public abstract class MethodInfo implements Comparable<MethodInfo> {

    public abstract String getName();

    public abstract ClassInfo getReturnType();

    public abstract List<ParameterInfo> getParameters();

    public abstract boolean isStatic();

    public String getSignature() {
        return String.format("%s(%s)", getName(), getParameterString(p -> p.getType().getName()));
    }

    @Override
    public int compareTo(MethodInfo o) {
        int i = getName().compareTo(o.getName());
        return i == 0 ? getSignature().compareTo(o.getSignature()) : i;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MethodInfo other && getSignature().equals(other.getSignature());
    }

    @Override
    public int hashCode() {
        return getSignature().hashCode();
    }

    @Override
    public String toString() {
        return getReturnType() != null ?
                String.format("%s(%s) → %s", getName(), getParameterString(p -> p.getType().getSimpleName()), getReturnType().getSimpleName()) :
                String.format("%s(%s)", getName(), getParameterString(p -> p.getType().getSimpleName()));
    }

    private String getParameterString(Function<ParameterInfo, String> function) {
        if (getParameters().isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (ParameterInfo parameter : getParameters()) {
            joiner.add(function.apply(parameter));
        }
        return joiner.toString();
    }
}
