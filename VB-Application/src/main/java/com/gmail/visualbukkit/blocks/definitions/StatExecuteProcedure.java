package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class StatExecuteProcedure extends Statement {

    public StatExecuteProcedure() {
        super("stat-execute-procedure", "Execute Procedure", "VB", "Executes a procedure");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter("Procedure"), new ExpressionParameter("Arguments", ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "PluginMain.procedure(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
