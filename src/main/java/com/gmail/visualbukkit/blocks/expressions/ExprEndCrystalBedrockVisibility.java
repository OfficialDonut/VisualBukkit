package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.EnderCrystal;

@Description("The visibility of the bedrock slate below an end crystal")
public class ExprEndCrystalBedrockVisibility extends ExpressionBlock<Boolean> {

    public ExprEndCrystalBedrockVisibility() {
        init("bedrock visibility of ", EnderCrystal.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isShowingBottom()";
    }
}
