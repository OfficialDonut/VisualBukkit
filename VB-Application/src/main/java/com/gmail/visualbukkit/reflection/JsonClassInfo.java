package com.gmail.visualbukkit.reflection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.TreeSet;

public class JsonClassInfo extends ClassInfo {

    private final JSONObject json;
    private Set<FieldInfo> fields;
    private Set<ConstructorInfo> constructors;
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
    public Set<FieldInfo> getFields() {
        if (fields == null) {
            fields = new TreeSet<>();
            JSONArray fieldsJson = json.optJSONArray("fields");
            if (fieldsJson != null) {
                for (Object o : fieldsJson) {
                    fields.add(new JsonFieldInfo((JSONObject) o));
                }
            }
        }
        return fields;
    }

    @Override
    public Set<ConstructorInfo> getConstructors() {
        if (constructors == null) {
            constructors = new TreeSet<>();
            JSONArray constructorsJson = json.optJSONArray("constructors");
            if (constructorsJson != null) {
                for (Object o : constructorsJson) {
                    constructors.add(new JsonConstructorInfo(this, (JSONObject) o));
                }
            }
        }
        return constructors;
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
