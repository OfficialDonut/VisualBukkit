package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Description("The unbreakable state of an item")
public class ExprItemUnbreakableState extends ExpressionBlock<Boolean> {

    public ExprItemUnbreakableState() {
        init("unbreakable state of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().isUnbreakable()";
    }
}
