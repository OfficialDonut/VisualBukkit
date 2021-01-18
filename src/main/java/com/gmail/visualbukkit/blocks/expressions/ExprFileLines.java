package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;
import java.util.List;

@Description("The lines of a file")
@SuppressWarnings("rawtypes")
public class ExprFileLines extends ExpressionBlock<List> {

    public ExprFileLines() {
        init("lines of ", File.class);
    }

    @Override
    public String toJava() {
        return "Files.readAllLines(" + arg(0) + ".toPath(),java.nio.charset.StandardCharsets.UTF_8)";
    }
}