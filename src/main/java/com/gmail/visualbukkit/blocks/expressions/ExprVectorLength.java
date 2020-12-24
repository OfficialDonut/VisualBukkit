package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The length of a vector")
public class ExprVectorLength extends ExpressionBlock<Double> {

    public ExprVectorLength() {
        init("length of ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".length()";
    }
}