package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Description("The material of a block")
public class ExprMaterialOfBlock extends ExpressionBlock<Material> {

    public ExprMaterialOfBlock() {
        init("material of ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}