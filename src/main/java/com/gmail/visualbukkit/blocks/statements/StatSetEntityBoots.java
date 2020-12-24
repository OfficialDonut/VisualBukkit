package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Category(Category.ENTITY)
@Description("Sets the boots of a living entity")
public class StatSetEntityBoots extends StatementBlock {

    public StatSetEntityBoots() {
        init("set boots of ", LivingEntity.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().setBoots(" + arg(1) + ");";
    }
}
