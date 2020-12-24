package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.InventoryView;

@Description("The title of an inventory view")
public class ExprInventoryViewTitle extends ExpressionBlock<String> {

    public ExprInventoryViewTitle() {
        init("title of ", InventoryView.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }
}