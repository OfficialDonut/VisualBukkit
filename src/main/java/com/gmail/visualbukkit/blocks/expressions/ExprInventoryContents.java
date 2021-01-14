package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.inventory.Inventory;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

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