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
}
