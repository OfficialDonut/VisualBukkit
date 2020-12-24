package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the custom name visibility state of an entity")
public class StatSetCustomNameVisibility extends StatementBlock {

    public StatSetCustomNameVisibility() {
        init("set custom name visibility of ", Entity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setCustomNameVisible(" + arg(1) + ");";
    }
}
