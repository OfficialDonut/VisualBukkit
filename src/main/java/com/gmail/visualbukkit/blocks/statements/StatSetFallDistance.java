package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the distance an entity has fallen")
public class StatSetFallDistance extends StatementBlock {

    public StatSetFallDistance() {
        init("set fall distance of ", Entity.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setFallDistance(" + arg(1) + ");";
    }
}
