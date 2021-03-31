package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class StatExecuteProcedure extends Statement {

    public StatExecuteProcedure() {
        super("stat-execute-procedure");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter(), new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "PluginMain.procedure(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
