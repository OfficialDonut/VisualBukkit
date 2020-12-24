package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;

import java.util.UUID;

@Name("Random UUID")
@Description("A random UUID")
public class ExprRandomUUID extends ExpressionBlock<UUID> {

    public ExprRandomUUID() {
        init("random UUID");
    }

    @Override
    public String toJava() {
        return "UUID.randomUUID()";
    }
}