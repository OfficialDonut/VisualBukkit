package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

@Description("The entity type spawned by a spawner")
public class ExprSpawnerEntityType extends ExpressionBlock<EntityType> {

    public ExprSpawnerEntityType() {
        init("entity type of ", CreatureSpawner.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSpawnedType()";
    }
}