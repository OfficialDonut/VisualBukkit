package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("Multiplies all components of a vector by a scalar")
public class ExprScaleVector extends ExpressionBlock<Vector> {

    public ExprScaleVector() {
        init(Vector.class, " * ", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".multiply(" + arg(1) + ")";
    }
}