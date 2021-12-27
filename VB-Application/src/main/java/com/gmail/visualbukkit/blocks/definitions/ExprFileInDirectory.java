package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileInDirectory extends Expression {

    public ExprFileInDirectory() {
        super("expr-file-in-directory", "File In Directory", "File", "A file in a directory");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(File.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Directory", ClassInfo.STRING), new ExpressionParameter("File", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "new File(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
