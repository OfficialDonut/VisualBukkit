package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

import java.util.List;

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