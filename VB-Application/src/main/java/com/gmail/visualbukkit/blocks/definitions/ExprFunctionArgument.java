package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(uid = "expr-function-argument", name = "Function Argument")
public class ExprFunctionArgument extends ExpressionBlock {

    public ExprFunctionArgument() {
        addParameter("Index", new ExpressionParameter(ClassInfo.of(int.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        if (!(getPluginComponentBlock() instanceof CompFunction)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava() {
        return "args.get(" + arg(0) + ")";
    }
}
