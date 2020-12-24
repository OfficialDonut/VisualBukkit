package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("The parent directory of a file")
public class ExprFileParent extends ExpressionBlock<File> {

    public ExprFileParent() {
        init("parent of ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getParentFile()";
    }
}