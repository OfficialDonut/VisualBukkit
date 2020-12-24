package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The silent state of an entity")
public class ExprSilentState extends ExpressionBlock<Boolean> {

    public ExprSilentState() {
        init("silent state of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isSilent()";
    }
}