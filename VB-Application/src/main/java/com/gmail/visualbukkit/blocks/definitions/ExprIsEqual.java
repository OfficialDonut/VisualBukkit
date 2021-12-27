package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprIsEqual extends Expression {

    public ExprIsEqual() {
        super("expr-is-equal", "Is Equal", "VB", "Checks if two objects are equal");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Object", ClassInfo.OBJECT), new ExpressionParameter("Object", ClassInfo.OBJECT)) {
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
            """
            public static boolean checkEquals(Object o1, Object o2) {
                if (o1 == null || o2 == null) {
                    return false;
                }
                return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
            }
            """;
}
