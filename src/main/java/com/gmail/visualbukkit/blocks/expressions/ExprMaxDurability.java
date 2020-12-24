package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

@Description("The max durability of a material")
public class ExprMaxDurability extends ExpressionBlock<Short> {

    public ExprMaxDurability() {
        init("max durability of ", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getMaxDurability()";
    }
}