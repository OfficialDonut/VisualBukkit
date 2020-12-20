package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructFunction;

@Description("Returns a value from a function")
public class StatFunctionReturn extends StatementBlock {

    public StatFunctionReturn() {
        init("return ", Object.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Return must be used in a function", StructFunction.class);
    }

    @Override
    public String toJava() {
        return "if (true) return " + arg(0) + ";";
    }
}
