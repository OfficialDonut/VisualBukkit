package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprIsEqual extends Expression {

    public ExprIsEqual() {
        super("expr-is-equal", boolean.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(Object.class), new ExpressionParameter(Object.class)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(EQUALS_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.checkEquals(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }

    private static final String EQUALS_METHOD =
            "public static boolean checkEquals(Object o1, Object o2) {\n" +
            "    if (o1 == null || o2 == null) {\n" +
            "        return false;\n" +
            "    }\n" +
            "    return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);\n" +
            "}";
}
