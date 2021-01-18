package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.inventory.Inventory;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@SuppressWarnings("rawtypes")
@Description("The viewers of an inventory")
public class ExprInventoryViewers extends ExpressionBlock<List> {

    public ExprInventoryViewers() {
        init("viewers of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getViewers()";
    }
}