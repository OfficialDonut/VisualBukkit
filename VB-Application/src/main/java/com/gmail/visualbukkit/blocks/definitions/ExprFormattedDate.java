package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.time.LocalDateTime;

public class ExprFormattedDate extends Expression {

    public ExprFormattedDate() {
        super("expr-formatted-date", "Formatted Date", "String", "Formats a date (https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Date", ClassInfo.of(LocalDateTime.class)), new ExpressionParameter("Format", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "java.time.format.DateTimeFormatter.ofPattern(" + arg(1) + ").format(" + arg(0) + ")";
            }
        };
    }
}
