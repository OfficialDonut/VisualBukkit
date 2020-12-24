package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Tameable;

@Category(Category.ENTITY)
@Description("Sets the tamed state of a tameable entity")
public class StatSetTamedState extends StatementBlock {

    public StatSetTamedState() {
        init("set tamed state of ", Tameable.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setTamed(" + arg(1) + ");";
    }
}
