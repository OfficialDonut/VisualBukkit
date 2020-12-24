package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

@Category(Category.ENTITY)
@Description("Sets the target of a mob")
public class StatSetMobTarget extends StatementBlock {

    public StatSetMobTarget() {
        init("set target of ", Mob.class, " to ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setTarget(" + arg(1) + ");";
    }
}
