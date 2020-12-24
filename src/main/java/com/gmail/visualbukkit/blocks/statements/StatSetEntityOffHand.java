package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Category(Category.ENTITY)
@Description("Sets the off hand item of a living entity")
public class StatSetEntityOffHand extends StatementBlock {

    public StatSetEntityOffHand() {
        init("set off hand item of ", LivingEntity.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().setItemInOffHand(" + arg(1) + ");";
    }
}
