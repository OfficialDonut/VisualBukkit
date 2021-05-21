package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprPathOfFile extends Expression {

    public ExprPathOfFile() {
        super("expr-path-of-file");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".getCanonicalPath()";
            }
        };
    }
}
