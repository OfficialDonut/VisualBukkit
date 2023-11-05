package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class LoadedMethodInfo extends MethodInfo {

    private final Method method;
    private List<ParameterInfo> parameters;

    protected LoadedMethodInfo(Method method) {
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(method.getReturnType().getCanonicalName());
    }

    @Override
    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<>(method.getParameterCount());
            for (Parameter parameter : method.getParameters()) {
                parameters.add(new LoadedParameterInfo(parameter));
            }
        }
        return parameters;
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }
}
