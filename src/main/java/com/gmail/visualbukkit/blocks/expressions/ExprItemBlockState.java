package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

@Description("The block state associated with an item")
public class ExprItemBlockState extends ExpressionBlock<BlockState> {

    public ExprItemBlockState() {
        init("block state of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((BlockStateMeta)" + arg(0) + ".getItemMeta()).getBlockState()";
    }
}