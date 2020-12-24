package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;

@Description("Checks if two objects are equal")
public class ExprEquals extends ExpressionBlock<Boolean> {

    public ExprEquals() {
        init(Object.class, " = ", Object.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(EQUALS_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.checkEquals(" + arg(0) + "," + arg(1) + ")";
    }

    private static final String EQUALS_METHOD =
            "public static boolean checkEquals(Object o1, Object o2) {\n" +
            "    if (o1 == null || o2 == null) {\n" +
            "        return false;\n" +
            "    }\n" +
            "    return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);\n" +
            "}";
}