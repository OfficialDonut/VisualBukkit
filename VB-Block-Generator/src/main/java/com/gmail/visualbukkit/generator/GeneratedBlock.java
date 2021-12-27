package com.gmail.visualbukkit.generator;

import org.json.JSONObject;

public class GeneratedBlock {

    private JSONObject json = new JSONObject();
    private boolean invalid;

    public void setInvalid() {
        invalid = true;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public JSONObject getJson() {
        return json;
    }
}
