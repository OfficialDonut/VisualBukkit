package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Removes an entity (kills it without spawning drops)")
public class StatRemoveEntity extends StatementBlock {

    public StatRemoveEntity() {
        init("remove ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".remove();";
    }
}
