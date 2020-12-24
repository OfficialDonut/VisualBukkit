package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Category(Category.ENTITY)
@Description("Sets the main hand item of a living entity")
public class StatSetEntityMainHand extends StatementBlock {

    public StatSetEntityMainHand() {
        init("set main hand item of ", LivingEntity.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().setItemInMainHand(" + arg(1) + ");";
    }
}
