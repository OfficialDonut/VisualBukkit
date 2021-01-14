package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The item flags of an item")
@SuppressWarnings("rawtypes")
public class ExprItemFlags extends ExpressionBlock<List> {

    public ExprItemFlags() {
        init("item flags of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getItemMeta().getItemFlags())";
    }
}