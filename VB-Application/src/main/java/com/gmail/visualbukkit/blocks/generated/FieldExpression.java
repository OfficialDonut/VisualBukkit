package com.gmail.visualbukkit.blocks.generated;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class FieldExpression extends Expression {

    private JSONObject json;
    private ClassInfo parameterType;
    private String parameterName;

    public FieldExpression(JSONObject json) {
        super(json.getString("id"), json.getString("field"), NameUtil.formatClassName(json.getString("class")), json.optString("descr"));
        this.json = json;
        JSONArray paramTypesArr = json.optJSONArray("param-types");
        JSONArray paramNamesArr = json.optJSONArray("param-names");
        if (paramTypesArr != null && paramNamesArr != null) {
            parameterType = ClassInfo.of(paramTypesArr.getString(0));
            parameterName = paramNamesArr.getString(0);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        ClassInfo returnType = ClassInfo.of(json.getString("return"));
        return returnType.isArrayOrCollection() ? ClassInfo.of(List.class) : returnType;
    }

    @Override
    public Block createBlock() {
        return new Block(this, parameterType != null ? new BlockParameter[]{new ExpressionParameter(parameterName, parameterType)} : new ExpressionParameter[0]) {
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
                return "((" + classInfo.getCanonicalClassName() + ")" + java + ")";
            }
        };
    }
}
