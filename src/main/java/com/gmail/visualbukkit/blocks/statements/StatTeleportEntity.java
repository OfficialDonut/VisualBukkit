package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Teleports an entity to a location")
public class StatTeleportEntity extends StatementBlock {

    public StatTeleportEntity() {
        init("teleport ", Entity.class, " to ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".teleport(" + arg(1) + ");";
    }
}
