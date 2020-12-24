package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The sum of two vectors")
public class ExprVectorAddition extends ExpressionBlock<Vector> {

    public ExprVectorAddition() {
        init(Vector.class, " + ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".add(" + arg(1) + ")";
    }
}