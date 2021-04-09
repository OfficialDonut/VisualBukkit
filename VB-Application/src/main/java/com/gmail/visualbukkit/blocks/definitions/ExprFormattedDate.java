package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprFormattedDate extends Expression {

    public ExprFormattedDate() {
        super("expr-formatted-date", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("java.time.LocalDateTime")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "java.time.format.DateTimeFormatter.ofPattern(" + arg(1) + ").format(" + arg(0) + ")";
            }
        };
    }
}
