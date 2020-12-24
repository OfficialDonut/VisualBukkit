package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@Description("The type of an entity")
public class ExprTypeOfEntity extends ExpressionBlock<EntityType> {

    public ExprTypeOfEntity() {
        init("type of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}