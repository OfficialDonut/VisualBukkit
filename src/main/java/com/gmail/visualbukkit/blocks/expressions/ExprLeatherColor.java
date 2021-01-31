package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

@Description("The color of a leather armor item")
public class ExprLeatherColor extends ExpressionBlock<Color> {

    public ExprLeatherColor() {
        init("leather color of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((org.bukkit.inventory.meta.LeatherArmorMeta)" + arg(0) + ".getItemMeta()).getColor()";
    }
}
