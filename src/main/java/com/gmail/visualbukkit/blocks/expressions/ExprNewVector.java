package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("A vector")
public class ExprNewVector extends ExpressionBlock<Vector> {

    public ExprNewVector() {
        init("vector(", double.class, ", ", double.class, ", ", double.class, ")");
    }

    @Override
    public String toJava() {
        return "new org.bukkit.util.Vector(" + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }
}