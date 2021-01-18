package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Description("The contents of an inventory")
@SuppressWarnings("rawtypes")
public class ExprInventoryContents extends ExpressionBlock<List> {

    public ExprInventoryContents() {
        init("contents of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getContents())";
    }
}