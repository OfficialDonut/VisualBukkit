package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("A file in a directory (it may or may not exist)")
public class ExprFileInDirectory extends ExpressionBlock<File> {

    public ExprFileInDirectory() {
        init("file ", String.class, " in directory ", File.class);
    }

    @Override
    public String toJava() {
        return "new File(" + arg(1) + "," + arg(0) + ")";
    }
}
