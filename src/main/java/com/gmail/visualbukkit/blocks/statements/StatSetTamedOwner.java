package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Tameable;

@Category(Category.ENTITY)
@Description("Sets the owner of a tameable entity")
public class StatSetTamedOwner extends StatementBlock {

    public StatSetTamedOwner() {
        init("set owner of ", Tameable.class, " to ", AnimalTamer.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setOwner(" + arg(1) + ");";
    }
}
