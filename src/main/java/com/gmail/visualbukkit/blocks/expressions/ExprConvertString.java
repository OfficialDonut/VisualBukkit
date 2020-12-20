package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.TypeHandler;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

import java.util.Map;
import java.util.function.Function;

@Description("Converts a string to an object")
public class ExprConvertString extends ExpressionBlock<Object> {

    private String convertMethod = null;

    public ExprConvertString() {
        init(String.class, " converted to ", new ChoiceParameter(TypeHandler.getStringParsers().keySet()));
    }

    @Override
    public void prepareBuild(BuildContext context) {
        if (convertMethod == null) {
            StringBuilder builder = new StringBuilder("private static Object convertString(String str, Class<?> clazz) { try {");
            for (Map.Entry<String, Function<String, String>> entry : TypeHandler.getStringParsers().entrySet()) {
                builder.append("if (clazz == ").append(TypeHandler.getType(entry.getKey()).getCanonicalName()).append(".class) {")
                        .append("return ").append(entry.getValue().apply("str")).append(";").append("}");
            }
            convertMethod = builder.append("} catch (Exception e) {} return null; }").toString();
        }
        context.addUtilMethods(convertMethod);
    }

    @Override
    public String toJava() {
        return "PluginMain.convertString(" + arg(0) + "," + TypeHandler.getType(arg(1)).getCanonicalName() + ".class)";
    }
}
