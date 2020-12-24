package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("Makes the length of a vector equal to 1")
public class ExprNormalizedVector extends ExpressionBlock<Vector> {

    public ExprNormalizedVector() {
        init(Vector.class, " normalized");
    }

    @Override
    public String toJava() {
        return arg(0) + ".normalize()";
    }
}