package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;

@Description("The inventory of a container block")
public class ExprContainerInventory extends ExpressionBlock<Inventory> {

    public ExprContainerInventory() {
        init("inventory of ", Container.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory()";
    }
}