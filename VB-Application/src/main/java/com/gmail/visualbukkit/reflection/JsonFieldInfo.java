package com.gmail.visualbukkit.reflection;

import org.json.JSONObject;

public class JsonFieldInfo extends FieldInfo {

    private final JSONObject json;

    protected JsonFieldInfo(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public ClassInfo getType() {
        return ClassInfo.of(json.getString("type"));
    }

    @Override
    public boolean isStatic() {
        return json.optBoolean("static");
    }
}
