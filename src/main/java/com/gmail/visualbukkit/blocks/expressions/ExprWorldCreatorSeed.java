package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Description("The seed of a world creator")
public class ExprWorldCreatorSeed extends ExpressionBlock<Long> {

    public ExprWorldCreatorSeed() {
        init("seed of ", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".seed()";
    }
}