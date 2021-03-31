package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatExecuteExpression extends Statement {

    public StatExecuteExpression() {
        super("stat-execute-expression");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return "Object " + ExprSimpleLocalVariable.getRandomVariable() + " = " + arg(0) + ";";
            }
        };
    }
}
