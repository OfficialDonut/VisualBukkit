package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

@Description("The block a living entity is looking at")
public class ExprTargetBlock extends ExpressionBlock<Block> {

    public ExprTargetBlock() {
        init("target block of ", LivingEntity.class, " with max distance ", int.class, " ", new ChoiceParameter("ignoring fluids", "including fluids", "including source fluids"));
    }

    @Override
    public String toJava() {
        String mode = arg(2).equals("ignoring fluids") ? "NEVER" : arg(2).equals("including fluids") ? "ALWAYS" : "SOURCE_ONLY";
        return arg(0) + ".getTargetBlockExact(" + arg(1) + ", FluidCollisionMode." + mode + ")";
    }
}