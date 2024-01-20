package com.gmail.visualbukkit.reflection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonConstructorInfo extends ConstructorInfo {

    private final JSONObject json;
    private List<ParameterInfo> parameters;

    protected JsonConstructorInfo(JsonClassInfo clazz, JSONObject json) {
        super(clazz);
        this.json = json;
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
}
