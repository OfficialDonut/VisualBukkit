package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.ElementInspector;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.util.PropertyGridPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface CodeBlock extends ElementInspector.Inspectable {

    String toJava();

    List<BlockParameter> getParameters();

    BlockDefinition<?> getDefinition();

    void delete();

    default void copy() {
        CopyPasteManager.copy(this);
    }

    default void cut() {
        copy();
        delete();
    }

    default void update() {
        getParameters().forEach(BlockParameter::update);
    }

    @Override
    default PropertyGridPane createInspectorPane() {
        PropertyGridPane gridPane = new PropertyGridPane();
        gridPane.addProperty("Name", getDefinition().getName());
        gridPane.addProperty("Description", getDefinition().getDescription());
        return gridPane;
    }

    default void prepareBuild(BuildContext context) {}

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
