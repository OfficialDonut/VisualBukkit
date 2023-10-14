package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;

@BlockDefinition(uid = "expr-invoke-method", name = "Invoke Method")
public class ExprInvokeMethod extends ExpressionBlock {

    @Override
    public String generateJava() {
        return null;
    }

    @Override
    public Class<?> getReturnType() {
        return null;
    }
}
