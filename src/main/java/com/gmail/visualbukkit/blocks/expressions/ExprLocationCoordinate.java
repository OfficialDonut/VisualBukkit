package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.Location;

@Description("A coordinate of a location")
public class ExprLocationCoordinate extends ExpressionBlock<Double> {

    public ExprLocationCoordinate() {
        init(new ChoiceParameter("x", "y", "z"), " of ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get" + arg(0).toUpperCase() + "()";
    }
}