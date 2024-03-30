package com.gmail.visualbukkit.reflection;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CtMethodInfo extends MethodInfo {

    private final CtMethod method;
    private List<ParameterInfo> parameters;

    protected CtMethodInfo(CtMethod method) {
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public ClassInfo getReturnType() {
        try {
            return method.getReturnType() != null ? ClassInfo.of(method.getReturnType().getName()) : null;
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<>();
            try {
                for (CtClass parameter : method.getParameterTypes()) {
                    parameters.add(new CtParameterInfo(parameter));
                }
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return parameters;
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }
}
