package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Bee;

@Category(Category.ENTITY)
@Description("Sets the anger level of a bee")
public class StatSetBeeAngerLevel extends StatementBlock {

    public StatSetBeeAngerLevel() {
        init("set anger level of ", Bee.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setAnger(" + arg(1) + ");";
    }
}
