package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("A file (it may or may not exist)")
public class ExprFile extends ExpressionBlock<File> {

    public ExprFile() {
        init("file ", String.class);
    }

    @Override
    public String toJava() {
        return "new File(" + arg(0) + ")";
    }
}