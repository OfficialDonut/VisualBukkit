package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

@Description("Arbitrary java code")
public class StatJavaCode extends StatementBlock {

    public StatJavaCode() {
        init("java ", new InputParameter());
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
