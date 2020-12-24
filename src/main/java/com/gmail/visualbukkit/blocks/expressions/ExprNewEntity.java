package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@Description("Spawns a new entity")
public class ExprNewEntity extends ExpressionBlock<Entity> {

    public ExprNewEntity() {
        init("new ", EntityType.class, " at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().spawnEntity(" + arg(1) + "," + arg(0) + ")";
    }
}