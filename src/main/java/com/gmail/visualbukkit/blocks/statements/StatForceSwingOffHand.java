package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Category(Category.ENTITY)
@Description("Forces a living entity to swing their off hand")
public class StatForceSwingOffHand extends StatementBlock {

    public StatForceSwingOffHand() {
        init("force ", LivingEntity.class, " to swing their off hand");
    }

    @Override
    public String toJava() {
        return arg(0) + ".swingOffHand();";
    }
}
