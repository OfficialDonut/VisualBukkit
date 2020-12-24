package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

@Description("An adjacent block to a block")
public class ExprRelativeBlock extends ExpressionBlock<Block> {

    public ExprRelativeBlock() {
        init("block ", BlockFace.class, " of ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getRelative(" + arg(0) + ")";
    }
}