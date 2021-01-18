package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

import java.util.List;

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