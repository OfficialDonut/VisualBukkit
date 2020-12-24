package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

@Description("The location of an inventory")
public class ExprInventoryLocation extends ExpressionBlock<Location> {

    public ExprInventoryLocation() {
        init("location of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}