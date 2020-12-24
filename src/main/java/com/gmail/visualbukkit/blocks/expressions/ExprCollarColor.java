package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.DyeColor;
import org.bukkit.entity.Wolf;

@Description("The collar color of a wolf")
public class ExprCollarColor extends ExpressionBlock<DyeColor> {

    public ExprCollarColor() {
        init("collar color of ", Wolf.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".getCollarColor()";
    }
}