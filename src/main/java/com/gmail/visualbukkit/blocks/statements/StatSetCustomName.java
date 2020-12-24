package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Nameable;

@Category(Category.ENTITY)
@Description("Sets the custom name of an entity")
public class StatSetCustomName extends StatementBlock {

    public StatSetCustomName() {
        init("set custom name of ", Nameable.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setCustomName(" + arg(1) + ");";
    }
}
