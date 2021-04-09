package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileName extends Expression {

    public ExprFileName() {
        super("expr-file-name", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".getName()";
            }
        };
    }
}
