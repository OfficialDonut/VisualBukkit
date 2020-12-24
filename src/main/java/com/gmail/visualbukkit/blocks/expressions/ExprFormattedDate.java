package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.time.LocalDateTime;

@Description("A formatted date")
public class ExprFormattedDate extends ExpressionBlock<String> {

    public ExprFormattedDate() {
        init(LocalDateTime.class, " with format ", String.class);
    }

    @Override
    public String toJava() {
        return "java.time.format.DateTimeFormatter.ofPattern(" + arg(1) + ").format(" + arg(0) + ")";
    }
}