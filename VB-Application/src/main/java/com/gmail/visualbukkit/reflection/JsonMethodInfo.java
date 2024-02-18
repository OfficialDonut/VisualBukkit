package com.gmail.visualbukkit.reflection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMethodInfo extends MethodInfo {

    private final JSONObject json;
    private List<ParameterInfo> parameters;

    protected JsonMethodInfo(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public ClassInfo getReturnType() {
        return json.has("return") ? ClassInfo.of(json.getString("return")) : null;
    }

    @Override
    public List<ParameterInfo> getParameters() {
        if (parameters == null) {
            JSONArray parametersJson = json.optJSONArray("parameters");
            if (parametersJson != null) {
                parameters = new ArrayList<>(parametersJson.length());
                for (Object o : parametersJson) {
                    parameters.add(new JsonParameterInfo((JSONObject) o));
                }
            } else {
                parameters = Collections.emptyList();
            }
        }
        return parameters;
    }

    @Override
    public boolean isStatic() {
        return json.optBoolean("static");
    }
}
