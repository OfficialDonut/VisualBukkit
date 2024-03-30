package com.gmail.visualbukkit.reflection;

import javassist.CtClass;

public class CtParameterInfo extends ParameterInfo {

    private final CtClass parameter;

    protected CtParameterInfo(CtClass parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return parameter.getSimpleName();
    }

    @Override
    public ClassInfo getType() {
        return ClassInfo.of(parameter.getName());
    }
}
