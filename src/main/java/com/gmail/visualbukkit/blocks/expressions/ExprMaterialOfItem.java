package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Description("The material of an item")
public class ExprMaterialOfItem extends ExpressionBlock<Material> {

    public ExprMaterialOfItem() {
        init("material of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}