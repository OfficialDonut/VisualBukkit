package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileName extends Expression {

    public ExprFileName() {
        super("expr-file-name", "File Name", "File", "The name of a file (includes extension)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("File", ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".getName()";
            }
        };
    }
}
