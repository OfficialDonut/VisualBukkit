package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class ExprFileFromPath extends Expression {

    public ExprFileFromPath() {
        super("expr-file-from-path", "File From Path", "File", "Represents the file with the specified path (the file may not exist and may be a directory)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(File.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Path", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "new java.io.File(" + arg(0) + ")";
            }
        };
    }
}
