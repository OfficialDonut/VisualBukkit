package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Forces a living entity to swing their main hand")
public class StatForceSwingMainHand extends StatementBlock {

    public StatForceSwingMainHand() {
        init("force ", LivingEntity.class, " to swing their main hand");
    }

    @Override
    public String toJava() {
        return arg(0) + ".swingMainHand();";
    }
}
