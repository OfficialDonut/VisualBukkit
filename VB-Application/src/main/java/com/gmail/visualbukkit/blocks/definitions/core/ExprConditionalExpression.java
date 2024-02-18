package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-conditional-expression", name = "Conditional Expression", description = "Returns one of two objects depending on a condition")
public class ExprConditionalExpression extends ExpressionBlock {

    public ExprConditionalExpression() {
        addParameter("Condition", new ExpressionParameter(ClassInfo.of(boolean.class)));
        addParameter("If True", new ExpressionParameter(ClassInfo.of(Object.class)));
        addParameter("If False", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "(" + arg(0, buildInfo) + " ? " + arg(1, buildInfo) + " : " + arg(2, buildInfo) + ")";
    }
}
