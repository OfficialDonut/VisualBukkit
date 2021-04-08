package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class StatJavaCode extends Statement {

    public StatJavaCode() {
        super("stat-java-code");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter()) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
