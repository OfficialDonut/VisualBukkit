package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.util.Vector;

@Description("The difference of two vectors")
public class ExprVectorSubtraction extends ExpressionBlock<Vector> {

    public ExprVectorSubtraction() {
        init(Vector.class, " - ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".subtract(" + arg(1) + ")";
    }
}