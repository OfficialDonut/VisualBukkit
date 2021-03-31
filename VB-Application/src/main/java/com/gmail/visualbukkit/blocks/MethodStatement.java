package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MethodStatement extends Statement {

    private JSONObject json;
    private List<ClassInfo> parameterTypes = new ArrayList<>();

    public MethodStatement(JSONObject json) {
        super(json.getString("id"));
        this.json = json;
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            for (Object obj : parameterArray) {
                parameterTypes.add(ClassInfo.of((String) obj));
            }
        }
    }

    @Override
    public Block createBlock() {
        return new Block(this, parameterTypes.stream().map(ExpressionParameter::new).toArray(ExpressionParameter[]::new)) {
            @Override
            public void update() {
                super.update();
                if (json.optBoolean("event-method")) {
                    checkForEvent(ClassInfo.of(json.getString("class")));
                }
            }

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
                StringJoiner parameterJoiner = new StringJoiner(",");
                for (int i = json.optBoolean("static") || json.optBoolean("event-method") ? 0 : 1; i < parameterTypes.size(); i++) {
                    parameterJoiner.add(arg(i));
                }
                String java;
                if (json.optBoolean("static")) {
                    java = ClassInfo.of(json.getString("class")).getCanonicalClassName();
                } else if (json.optBoolean("event-method")) {
                    java = "event";
                } else {
                    java = arg(0);
                }
                return java + "." + json.getString("method") + "(" + parameterJoiner + ");";
            }
        };
    }
}
