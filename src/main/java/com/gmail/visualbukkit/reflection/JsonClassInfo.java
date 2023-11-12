package com.gmail.visualbukkit.reflection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.TreeSet;

public class JsonClassInfo extends ClassInfo {

    private final JSONObject json;
    private Set<MethodInfo> methods;

    protected JsonClassInfo(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getSimpleName() {
        return json.getString("simple-name");
    }

    @Override
    public String getPackage() {
        return json.getString("package");
    }

    @Override
    public Set<MethodInfo> getMethods() {
        if (methods == null) {
            methods = new TreeSet<>();
            JSONArray methodsJson = json.optJSONArray("methods");
            if (methodsJson != null) {
                for (Object o : methodsJson) {
                    methods.add(new JsonMethodInfo((JSONObject) o));
                }
            }
        }
        return methods;
    }
}
