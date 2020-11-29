package com.gmail.visualbukkit.blocks.components;

import org.json.JSONObject;

public interface BlockParameter {

    String toJava();

    JSONObject serialize();

    void deserialize(JSONObject obj);

    default void update() {}
}
