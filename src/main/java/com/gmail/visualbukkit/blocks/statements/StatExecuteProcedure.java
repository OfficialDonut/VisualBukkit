package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;

import java.util.List;

@Description("Executes a procedure")
public class StatExecuteProcedure extends StatementBlock {

    public StatExecuteProcedure() {
        init("execute procedure ", new StringLiteralParameter(), " with args ", List.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.procedure(" + arg(0) + "," + arg(1) + ");";
    }
}
