package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileExists extends Expression {

    public ExprFileExists() {
        super("expr-file-exists", "File Exists", "File", "Checks if a file exists");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("File", ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".exists()";
            }
        };
    }
}
