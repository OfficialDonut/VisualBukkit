package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Bee;

@Description("The anger level of a bee")
public class ExprBeeAngerLevel extends ExpressionBlock<Integer> {

    public ExprBeeAngerLevel() {
        init("anger level of ", Bee.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getAnger()";
    }
}