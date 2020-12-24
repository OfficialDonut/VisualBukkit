package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("Checks if a file is a directory")
public class ExprIsDirectory extends ExpressionBlock<Boolean> {

    public ExprIsDirectory() {
        init(File.class, " is a directory");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isDirectory()";
    }
}