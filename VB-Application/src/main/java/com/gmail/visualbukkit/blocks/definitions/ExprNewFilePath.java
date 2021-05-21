package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprNewFilePath extends Expression {

    public ExprNewFilePath() {
        super("expr-new-file-path");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(File.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "new java.io.File(" + arg(0) + ")";
            }
        };
    }
}
