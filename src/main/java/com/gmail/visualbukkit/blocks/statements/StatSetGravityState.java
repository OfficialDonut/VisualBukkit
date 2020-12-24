package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the gravity state of an entity")
public class StatSetGravityState extends StatementBlock {

    public StatSetGravityState() {
        init("set gravity state of ", Entity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setGravity(" + arg(1) + ");";
    }
}
