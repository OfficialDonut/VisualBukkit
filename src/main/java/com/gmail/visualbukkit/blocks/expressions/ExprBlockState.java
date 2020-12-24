package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

@Description("The state of a block")
public class ExprBlockState extends ExpressionBlock<BlockState> {

    public ExprBlockState() {
        init("state of ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getState()";
    }
}