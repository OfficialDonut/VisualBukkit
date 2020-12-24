package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.time.LocalDateTime;

@Description("The current date")
public class ExprCurrentDate extends ExpressionBlock<LocalDateTime> {

    public ExprCurrentDate() {
        init("current date");
    }

    @Override
    public String toJava() {
        return "java.time.LocalDateTime.now()";
    }
}