package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("Checks if a file exists")
public class ExprFileExists extends ExpressionBlock<Boolean> {

    public ExprFileExists() {
        init(File.class, " exists");
    }

    @Override
    public String toJava() {
        return arg(0) + ".exists()";
    }
}