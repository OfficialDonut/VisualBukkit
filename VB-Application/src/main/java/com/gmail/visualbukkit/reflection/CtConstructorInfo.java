package com.gmail.visualbukkit.reflection;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CtConstructorInfo extends ConstructorInfo {

    private final CtConstructor constructor;
    private List<ParameterInfo> parameters;

    protected CtConstructorInfo(CtConstructor constructor) {
        super(ClassInfo.of(constructor.getDeclaringClass().getName()));
        this.constructor = constructor;
    }

    @Override
    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<>();
            try {
                for (CtClass parameter : constructor.getParameterTypes()) {
                    parameters.add(new CtParameterInfo(parameter));
                }
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return parameters;
    }
}
