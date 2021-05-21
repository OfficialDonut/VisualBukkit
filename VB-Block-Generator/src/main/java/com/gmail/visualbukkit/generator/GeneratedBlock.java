package com.gmail.visualbukkit.generator;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneratedBlock implements Comparable<GeneratedBlock> {

    private JSONObject json = new JSONObject();
    private Map<String, String> langMap = new HashMap<>();
    private String id;
    private boolean invalid;

    public GeneratedBlock(String id) {
        this.id = id;
    }

    public void addLang(String key, String value) {
        langMap.put(id + "." + key, value);
    }

    public void setInvalid() {
        invalid = true;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public JSONObject getJson() {
        return json;
    }

    public Map<String, String> getLangMap() {
        return Collections.unmodifiableMap(langMap);
    }

    public String getID() {
        return id;
    }

    @Override
    public int compareTo(GeneratedBlock other) {
        return id.compareTo(other.id);
    }
}
