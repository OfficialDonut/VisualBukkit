package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class LoadedConstructorInfo extends ConstructorInfo {

    private final Constructor<?> constructor;
    private List<ParameterInfo> parameters;

    protected LoadedConstructorInfo(Constructor<?> constructor) {
        super(ClassInfo.of(constructor.getDeclaringClass()));
        this.constructor = constructor;
    }

    @Override
    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<>(constructor.getParameterCount());
            for (Parameter parameter : constructor.getParameters()) {
                parameters.add(new LoadedParameterInfo(parameter));
            }
        }
        return parameters;
    }
}
