package com.gmail.visualbukkit.reflection;

import org.json.JSONObject;

public class JsonParameterInfo extends ParameterInfo {

    private final JSONObject json;

    protected JsonParameterInfo(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public ClassInfo getType() {
        return ClassInfo.of(json.optString("type"));
    }
}
