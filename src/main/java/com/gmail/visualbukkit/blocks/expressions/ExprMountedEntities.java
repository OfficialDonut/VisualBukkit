package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

import java.util.List;

@Description("The entities riding an entity")
@SuppressWarnings("rawtypes")
public class ExprMountedEntities extends ExpressionBlock<List> {

    public ExprMountedEntities() {
        init("entities mounted on ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPassengers()";
    }
}