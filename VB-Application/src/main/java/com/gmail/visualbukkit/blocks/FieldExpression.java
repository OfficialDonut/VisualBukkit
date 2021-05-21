package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import org.json.JSONObject;

import java.util.List;

public class FieldExpression extends Expression {

    private JSONObject json;

    public FieldExpression(JSONObject json) {
        super(json.getString("id"));
        this.json = json;
    }

    @Override
    public ClassInfo getReturnType() {
        ClassInfo returnType = ClassInfo.of(json.getString("return"));
        return returnType.isArrayOrCollection() ? ClassInfo.of(List.class) : returnType;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                if (json.optBoolean("event")) {
                    checkForEvent(ClassInfo.of(json.getString("class")));
                }
            }

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                PluginModule pluginModule = PluginModule.get(json.optString("module"));
                if (pluginModule != null) {
                    buildContext.addPluginModule(pluginModule);
                }
            }

            @Override
            public String toJava() {
                String java = (json.optBoolean("static") ? ClassInfo.of(json.getString("class")).getCanonicalClassName() : arg(0)) + "." + json.getString("field");
                ClassInfo classInfo = ClassInfo.of(json.getString("return"));
                if (classInfo.isArrayOrCollection()) {
                    return classInfo.isList() ? ("((List)" + java + ")") : ("PluginMain.createList(" + java + ")");
                }
                return java;
            }
        };
    }
}
