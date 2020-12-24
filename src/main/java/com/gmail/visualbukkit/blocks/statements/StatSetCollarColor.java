package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.DyeColor;
import org.bukkit.entity.Wolf;

@Category(Category.ENTITY)
@Description("The collar color of a wolf")
public class StatSetCollarColor extends StatementBlock {

    public StatSetCollarColor() {
        init("set collar color of ", Wolf.class, " to ", DyeColor.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setCollarColor(" + arg(1) + ");";
    }
}
