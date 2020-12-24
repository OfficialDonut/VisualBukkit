package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

@Category(Category.ENTITY)
@Description("Sets the velocity of an entity")
public class StatSetEntityVelocity extends StatementBlock {

    public StatSetEntityVelocity() {
        init("set velocity of ", Entity.class, " to ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setVelocity(" + arg(1) + ");";
    }
}
