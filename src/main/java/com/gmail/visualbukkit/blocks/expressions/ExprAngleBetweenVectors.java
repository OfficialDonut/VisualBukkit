package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The angle between two vectors in radians")
public class ExprAngleBetweenVectors extends ExpressionBlock<Float> {

    public ExprAngleBetweenVectors() {
        init("angle between ", Vector.class, " and ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".angle(" + arg(1) + ")";
    }
}