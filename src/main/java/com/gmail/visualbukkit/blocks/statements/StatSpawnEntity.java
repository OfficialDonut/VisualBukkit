package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Category(Category.ENTITY)
@Description("Spawns an entity")
public class StatSpawnEntity extends StatementBlock {

    public StatSpawnEntity() {
        init("spawn ", EntityType.class, " at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().spawnEntity(" + arg(1) + "," + arg(0) + ");";
    }
}
