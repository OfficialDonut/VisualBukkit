package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Parameter;

public class LoadedParameterInfo extends ParameterInfo {

    private final Parameter parameter;

    protected LoadedParameterInfo(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return parameter.getName();
    }

    @Override
    public ClassInfo getType() {
        return ClassInfo.of(parameter.getType().getCanonicalName());
    }
}
