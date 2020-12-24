package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;

@Description("The data of a block")
public class ExprBlockData extends ExpressionBlock<Byte> {

    public ExprBlockData() {
        init("data of ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getData()";
    }
}