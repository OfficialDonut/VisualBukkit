package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class StatJavaCode extends Statement {

    public StatJavaCode() {
        super("stat-java-code", "Java Code", "VB", "Raw Java code");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter("Java")) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
