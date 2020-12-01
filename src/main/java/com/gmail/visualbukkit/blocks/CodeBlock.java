package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface CodeBlock {

    String toJava();

    List<BlockParameter> getParameters();

    BlockDefinition<?> getDefinition();

    default void prepareBuild(BuildContext context) {}

    default void update() {
        getParameters().forEach(BlockParameter::update);
    }

    default String arg(int i) {
        return getParameters().get(i).toJava();
    }

    default JSONObject serialize() {
        JSONObject obj = new JSONObject();
        JSONArray parameterArray = new JSONArray();
        for (BlockParameter parameter : getParameters()) {
            parameterArray.put(parameter.serialize());
        }
        obj.put("parameters", parameterArray);
        return obj;
    }

    default void deserialize(JSONObject obj) {
        List<BlockParameter> parameters = getParameters();
        JSONArray parameterArray = obj.optJSONArray("parameters");
        if (parameterArray != null) {
            int len = Math.min(parameters.size(), parameterArray.length());
            for (int i = 0; i < len; i++) {
                JSONObject parameterObj = parameterArray.optJSONObject(i);
                if (parameterObj != null) {
                    parameters.get(i).deserialize(parameterObj);
                }
            }
        }
    }
}
