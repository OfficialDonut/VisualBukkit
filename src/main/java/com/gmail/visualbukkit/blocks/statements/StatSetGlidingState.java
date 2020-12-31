package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Sets the gliding state of a living entity")
public class StatSetGlidingState extends StatementBlock {

    public StatSetGlidingState() {
        init("set gliding state of ", LivingEntity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setGliding(" + arg(1) + ");";
    }
}
