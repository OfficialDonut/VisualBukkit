package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The glowing state of an entity")
public class ExprGlowingState extends ExpressionBlock<Boolean> {

    public ExprGlowingState() {
        init("glowing state of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isGlowing()";
    }
}