package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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