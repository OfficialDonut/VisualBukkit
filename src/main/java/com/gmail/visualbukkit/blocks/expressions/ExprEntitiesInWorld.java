package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.World;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The entities in a world")
@SuppressWarnings("rawtypes")
public class ExprEntitiesInWorld extends ExpressionBlock<List> {

    public ExprEntitiesInWorld() {
        init("entities in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEntities()";
    }
}