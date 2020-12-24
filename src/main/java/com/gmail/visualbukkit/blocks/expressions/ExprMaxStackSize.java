package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

@Description("The max stack size of a material")
public class ExprMaxStackSize extends ExpressionBlock<Integer> {

    public ExprMaxStackSize() {
        init("max stack size of ", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getMaxStackSize()";
    }
}