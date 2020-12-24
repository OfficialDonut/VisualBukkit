package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the number of ticks before an entity stops being on fire")
public class StatSetEntityFireTicks extends StatementBlock {

    public StatSetEntityFireTicks() {
        init("set fire ticks of ", Entity.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setFireTicks(" + arg(1) + ");";
    }
}
