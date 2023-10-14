package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;
import java.util.function.Function;

public record MethodInfo(Method method) implements Comparable<MethodInfo> {

    @Override
    public int compareTo(MethodInfo o) {
        int i = method.getName().compareTo(o.method.getName());
        return i == 0 ? Integer.compare(method.getParameterCount(), o.method.getParameterCount()) : i;
    }

    @Override
    public String toString() {
        String parameters = getParameterString(p -> ClassInfo.toString(p.getType(), false));
        return method.getReturnType() != void.class ?
                String.format("%s(%s) → %s", method.getName(), parameters, ClassInfo.toString(method.getReturnType(), false)) :
                String.format("%s(%s)", method.getName(), parameters);
    }

    public String getSignature() {
        return String.format("%s(%s)", method.getName(), getParameterString(p -> p.getType().getName()));
    }

    private String getParameterString(Function<Parameter, String> function) {
        if (method.getParameterCount() == 0) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (Parameter parameter : method.getParameters()) {
            joiner.add(function.apply(parameter));
        }
        return joiner.toString();
    }
}
