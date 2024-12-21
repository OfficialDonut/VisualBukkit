package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-is-equal", name = "Is Equal", description = "Checks if two objects are equal")
public class ExprIsEqual extends ExpressionBlock {

    private final ExpressionParameter expr1 = new ExpressionParameter(ClassInfo.OBJECT_OR_PRIMITIVE);
    private final ExpressionParameter expr2 = new ExpressionParameter(ClassInfo.OBJECT_OR_PRIMITIVE);

    public ExprIsEqual() {
        addParameter("Object", expr1);
        addParameter("Object", expr2);
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (expr1.isReturnValuePrimitiveNumber() && expr2.isReturnValuePrimitiveNumber()) {
            return "(" + arg(0, buildInfo) + " == " + arg(1, buildInfo) + ")";
        }
        if (buildInfo.getMetadata().putIfAbsent("checkEquals()", true) == null) {
            buildInfo.getMainClass().addMethod("public static boolean checkEquals(Object o1, Object o2) { return o1 != null ? o1.equals(o2) : false; }");
        }
        return "PluginMain.checkEquals(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ")";
    }
}
