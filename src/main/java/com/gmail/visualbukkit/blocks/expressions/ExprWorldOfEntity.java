package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;
import org.bukkit.entity.Entity;

@Description("The world of an entity")
public class ExprWorldOfEntity extends ExpressionBlock<World> {

    public ExprWorldOfEntity() {
        init("world of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld()";
    }
}