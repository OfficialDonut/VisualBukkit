package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Sets the swimming state of a living entity")
public class StatSetSwimmingState extends StatementBlock {

    public StatSetSwimmingState() {
        init("set swimming state of ", LivingEntity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSwimming(" + arg(1) + ");";
    }
}
