package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MethodExpression extends Expression {

    private Class<?> clazz;
    private String method;
    private boolean isStatic;
    private PluginModule pluginModule;
    private List<Class<?>> parameterTypes = new ArrayList<>();

    public MethodExpression(JSONObject json) {
        super(json.getString("id"), BlockRegistry.getClass(json.getString("return")));
        clazz = BlockRegistry.getClass(json.getString("class"));
        method = json.getString("method");
        pluginModule = PluginModule.get(json.optString("plugin-module"));
        isStatic = json.optBoolean("static");
        if (!isStatic && !Event.class.isAssignableFrom(clazz)) {
            parameterTypes.add(clazz);
        }
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
            @SuppressWarnings("unchecked")
            public void update() {
                super.update();
                if (Event.class.isAssignableFrom(clazz)) {
                    checkForEvent((Class<? extends Event>) clazz);
                }
            }

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
                for (int i = isStatic || Event.class.isAssignableFrom(clazz) ? 0 : 1; i < parameterTypes.size(); i++) {
                    parameterJoiner.add(arg(i));
                }
                String java;
                if (isStatic) {
                    java = clazz.getCanonicalName();
                } else if (Event.class.isAssignableFrom(clazz)) {
                    java = "event";
                } else {
                    java = arg(0);
                }
                java += "." + method + "(" + parameterJoiner + ")";
                return TypeHandler.isCollection(getReturnType()) && !List.class.isAssignableFrom(getReturnType()) ? "PluginMain.createList(" + java + ")" : java;
            }
        };
    }
}
