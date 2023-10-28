package com.gmail.visualbukkit.blocks.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class MethodInfo implements Comparable<MethodInfo> {

    private final JSONObject json;
    private List<ParameterInfo> parameters;

    protected MethodInfo(JSONObject json) {
        this.json = json;
    }

    public String getSignature() {
        return String.format("%s(%s)", getName(), getParameterString(p -> p.getType().getName()));
    }

    public String getName() {
        return json.getString("name");
    }

    public ClassInfo getReturnType() {
        return json.has("return") ? ClassInfo.of(json.getString("return")) : null;
    }

    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            JSONArray parametersJson = json.optJSONArray("parameters");
            if (parametersJson != null) {
                parameters = new ArrayList<>();
                for (Object o : parametersJson) {
                    parameters.add(new ParameterInfo((JSONObject) o));
                }
            } else {
                parameters = Collections.emptyList();
            }
        }
        return parameters;
    }

    public boolean isStatic() {
        return json.optBoolean("static");
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
