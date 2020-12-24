package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

@Category(Category.ENTITY)
@Description("Sets the entity type spawned by a spawner")
public class StatSetSpawnerEntityType extends StatementBlock {

    public StatSetSpawnerEntityType() {
        init("set entity type of ", CreatureSpawner.class, " to ", EntityType.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSpawnedType(" + arg(1) + ");";
    }
}
