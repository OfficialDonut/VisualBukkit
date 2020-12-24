package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("A random vector with components between 0 and 1")
public class ExprRandomVector extends ExpressionBlock<Vector> {

    public ExprRandomVector() {
        init("random vector");
    }

    @Override
    public String toJava() {
        return "org.bukkit.util.Vector.getRandom()";
    }
}