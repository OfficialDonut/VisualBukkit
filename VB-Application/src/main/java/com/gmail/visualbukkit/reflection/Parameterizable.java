package com.gmail.visualbukkit.reflection;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public interface Parameterizable {

    List<ParameterInfo> getParameters();

    default String getParameterString(Function<ParameterInfo, String> function) {
        if (getParameters().isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (ParameterInfo parameter : getParameters()) {
            joiner.add(function.apply(parameter));
        }
        return joiner.toString();
    }

    default boolean checkParameterTypes(ClassInfo... types) {
        List<ParameterInfo> parameters = getParameters();
        if (parameters.size() == types.length) {
            for (int i = 0; i < types.length; i++) {
                if (!types[i].equals(parameters.get(i).getType())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
