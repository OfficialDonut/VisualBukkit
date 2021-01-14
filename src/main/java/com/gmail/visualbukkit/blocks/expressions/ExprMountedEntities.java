package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.entity.Entity;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

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