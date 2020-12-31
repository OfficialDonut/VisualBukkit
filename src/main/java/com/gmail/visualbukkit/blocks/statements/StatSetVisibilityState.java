package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Sets the visibility state of a living entity")
public class StatSetVisibilityState extends StatementBlock {

    public StatSetVisibilityState() {
        init("set visibility state of ", LivingEntity.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setInvisible(!" + arg(1) + ");";
    }
}
