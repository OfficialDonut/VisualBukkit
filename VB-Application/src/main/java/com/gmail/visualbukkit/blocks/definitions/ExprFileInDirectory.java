package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileInDirectory extends Expression {

    public ExprFileInDirectory() {
        super("expr-file-in-directory", ClassInfo.of(File.class));
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "new File(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
