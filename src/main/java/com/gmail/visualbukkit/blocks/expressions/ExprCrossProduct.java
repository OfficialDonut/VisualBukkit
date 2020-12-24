package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The cross product of two vectors")
public class ExprCrossProduct extends ExpressionBlock<Vector> {

    public ExprCrossProduct() {
        init("cross product of ", Vector.class, " and ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".crossProduct(" + arg(1) + ")";
    }
}