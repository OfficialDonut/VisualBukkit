package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Wolf;

@Description("The anger state of a wolf")
public class ExprWolfAngerState extends ExpressionBlock<Boolean> {

    public ExprWolfAngerState() {
        init("anger state of ", Wolf.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isAngry()";
    }
}