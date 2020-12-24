package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The custom name visibility state of an entity")
public class ExprCustomNameVisibility extends ExpressionBlock<Boolean> {

    public ExprCustomNameVisibility() {
        init("custom name visibility of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isCustomNameVisible()";
    }
}