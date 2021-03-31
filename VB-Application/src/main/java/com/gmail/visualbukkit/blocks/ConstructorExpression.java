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

    private JSONObject json;
    private List<ClassInfo> parameterTypes = new ArrayList<>();

    public ConstructorExpression(JSONObject json) {
        super(json.getString("id"), ClassInfo.of(json.getString("class")));
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
                for (BlockParameter parameter : getParameters()) {
                    parameterJoiner.add(parameter.toJava());
                }
                return "new " + ClassInfo.of(json.getString("class")).getCanonicalClassName() + "(" + parameterJoiner + ")";
            }
        };
    }
}
