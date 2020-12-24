package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("The name of a world")
public class ExprWorldName extends ExpressionBlock<String> {

    public ExprWorldName() {
        init("name of ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}