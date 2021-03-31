package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.json.JSONObject;

import java.util.List;

public class FieldExpression extends Expression {

    private JSONObject json;

    public FieldExpression(JSONObject json) {
        super(json.getString("id"), ClassInfo.of(json.getString("return")).isArrayOrCollection() ? ClassInfo.of(List.class) : ClassInfo.of(json.getString("return")));
        this.json = json;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                PluginModule pluginModule = PluginModule.get(json.optString("plugin-module"));
                if (pluginModule != null) {
                    buildContext.addPluginModule(pluginModule);
                }
            }

            @Override
            public String toJava() {
                String java = (json.optBoolean("static") ? ClassInfo.of(json.getString("class")).getCanonicalClassName() : arg(0)) + "." + json.getString("field");
                ClassInfo classInfo = ClassInfo.of(json.getString("return"));
                return classInfo.isArrayOrCollection() && (classInfo.getClazz() == null || !List.class.isAssignableFrom(classInfo.getClazz())) ? "PluginMain.createList(" + java + ")" : java;
            }
        };
    }
}
