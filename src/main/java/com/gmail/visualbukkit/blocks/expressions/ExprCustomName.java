package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Nameable;

@Description("The custom name of an entity")
public class ExprCustomName extends ExpressionBlock<String> {

    public ExprCustomName() {
        init("custom name of ", Nameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCustomName()";
    }
}