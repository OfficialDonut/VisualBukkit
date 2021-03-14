package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ConstructorExpression extends Expression {

    private Class<?> clazz;
    private PluginModule pluginModule;
    private List<Class<?>> parameterTypes = new ArrayList<>();

    public ConstructorExpression(JSONObject json) {
        super(json.getString("id"), BlockRegistry.getClass(json.getString("class")));
        clazz = BlockRegistry.getClass(json.getString("class"));
        pluginModule = PluginModule.get(json.optString("plugin-module"));
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            for (Object obj : parameterArray) {
                parameterTypes.add(BlockRegistry.getClass((String) obj));
            }
        }
    }

    @Override
    public Block createBlock() {
        return new Block(this, parameterTypes.stream().map(ExpressionParameter::new).toArray(ExpressionParameter[]::new)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                if (pluginModule != null) {
                    buildContext.addPluginModule(pluginModule);
                }
            }

            @Override
            public String toJava() {
                StringJoiner parameterJoiner = new StringJoiner(",");
                for (BlockParameter parameter : getParameters()) {
                    parameterJoiner.add(parameter.toJava());
                }
                return "new " + clazz.getCanonicalName() + "(" + parameterJoiner + ")";
            }
        };
    }
}
