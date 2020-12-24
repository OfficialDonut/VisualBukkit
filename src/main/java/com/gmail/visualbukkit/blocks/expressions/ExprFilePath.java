package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("The path of a file")
public class ExprFilePath extends ExpressionBlock<String> {

    public ExprFilePath() {
        init("path of ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCanonicalPath()";
    }
}