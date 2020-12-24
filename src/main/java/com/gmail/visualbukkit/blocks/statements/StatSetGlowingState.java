package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Sets the glowing state of an entity")
public class StatSetGlowingState extends StatementBlock {

    public StatSetGlowingState() {
        init("set glowing state of ", Entity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setGlowing(" + arg(1) + ");";
    }
}
