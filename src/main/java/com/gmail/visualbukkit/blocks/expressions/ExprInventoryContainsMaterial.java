package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

@Description("Checks if an inventory contains a material")
public class ExprInventoryContainsMaterial extends ExpressionBlock<Boolean> {

    public ExprInventoryContainsMaterial() {
        init(Inventory.class, " contains at least ", int.class, " ", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".contains(" + arg(2) + "," + arg(1) + ")";
    }
}