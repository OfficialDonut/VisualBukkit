package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.util.Vector;

@Description("A component of a vector")
public class ExprVectorComponent extends ExpressionBlock<Double> {

    public ExprVectorComponent() {
        init(new ChoiceParameter("x", "y", "z"), " of ", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get" + arg(0).toUpperCase() + "()";
    }
}