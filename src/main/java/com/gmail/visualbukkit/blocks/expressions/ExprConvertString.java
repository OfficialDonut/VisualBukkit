package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.TypeHandler;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

@Description("Converts a string to an object")
public class ExprConvertString extends ExpressionBlock<Object> {

    private static Set<String> types = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private static Map<String, String> convertMethods = new HashMap<>();
    private static Map<String, Integer> identifiers = new HashMap<>();

    static {
        int i = 0;
        for (Map.Entry<String, Function<String, String>> entry : TypeHandler.getStringParsers().entrySet()) {
            String type = entry.getKey();
            types.add(type);
            identifiers.put(type, i);
            convertMethods.put(type,
                    "public static Object convertString" + i++ + "(String str) {" +
                    "try {" +
                    "return " + entry.getValue().apply("str") + ";" +
                    "} catch (Exception e) { return null; }" +
                    "}");
        }
    }

    public ExprConvertString() {
        init(String.class, " converted to ", new ChoiceParameter(types));
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(convertMethods.get(arg(1)));
    }

    @Override
    public String toJava() {
        return "PluginMain.convertString" + identifiers.get(arg(1)) + "(" + arg(0) + ")";
    }
}
