package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FieldExpression extends Expression {

    private Class<?> clazz;
    private String field;
    private boolean isStatic;
    private PluginModule pluginModule;
    private List<Class<?>> parameterTypes = new ArrayList<>();

    public FieldExpression(JSONObject json) {
        super(json.getString("id"), BlockRegistry.getClass(json.getString("return")));
        clazz = BlockRegistry.getClass(json.getString("class"));
        field = json.getString("field");
        pluginModule = PluginModule.get(json.optString("plugin-module"));
        isStatic = json.optBoolean("static");
        if (!isStatic) {
            parameterTypes.add(clazz);
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
                String java = (isStatic ? clazz.getCanonicalName() : arg(0)) + "." + field;
                return TypeHandler.isCollection(getReturnType()) && !List.class.isAssignableFrom(getReturnType()) ? "PluginMain.createList(" + java + ")" : java;
            }
        };
    }
}
