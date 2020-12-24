package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The dot product of two vectors")
public class ExprDotProduct extends ExpressionBlock<Double> {

    public ExprDotProduct() {
        init("dot product of ", Vector.class, " and ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".dot(" + arg(1) + ")";
    }
}