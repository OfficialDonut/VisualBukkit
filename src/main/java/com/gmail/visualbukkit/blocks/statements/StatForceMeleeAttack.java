package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Forces a living entity to attack another entity")
public class StatForceMeleeAttack extends StatementBlock {

    public StatForceMeleeAttack() {
        init("force ", LivingEntity.class, " to attack ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".attack(" + arg(1) + ");";
    }
}
