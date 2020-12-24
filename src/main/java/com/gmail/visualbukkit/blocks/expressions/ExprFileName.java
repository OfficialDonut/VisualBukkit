package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("The name of a file (includes extension)")
public class ExprFileName extends ExpressionBlock<String> {

    public ExprFileName() {
        init("name of ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}