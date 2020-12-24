package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the material of an item")
public class StatSetMaterialOfItem extends StatementBlock {

    public StatSetMaterialOfItem() {
        init("set material of ", ItemStack.class, " to ", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setType(" + arg(1) + ");";
    }
}
