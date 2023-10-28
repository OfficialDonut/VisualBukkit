package com.gmail.visualbukkit.blocks.classes;

import org.json.JSONObject;

public class ParameterInfo {

    private final JSONObject json;

    protected ParameterInfo(JSONObject json) {
        this.json = json;
    }

    public String getName() {
        return json.getString("name");
    }

    public ClassInfo getType() {
        return ClassInfo.of(json.optString("type"));
    }
}
