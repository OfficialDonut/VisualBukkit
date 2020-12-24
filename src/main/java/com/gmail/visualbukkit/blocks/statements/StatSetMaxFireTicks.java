package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the max fire ticks of an entity")
public class StatSetMaxFireTicks extends StatementBlock {

    public StatSetMaxFireTicks() {
        init("set max fire ticks of ", Entity.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setMaxFireTicks(" + arg(1) + ");";
    }
}
