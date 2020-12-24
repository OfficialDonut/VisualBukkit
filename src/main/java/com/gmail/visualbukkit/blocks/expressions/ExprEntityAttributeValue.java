package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;

@Description("The value of an entity attribute")
public class ExprEntityAttributeValue extends ExpressionBlock<Double> {

    public ExprEntityAttributeValue() {
        init("value of ", Attribute.class, " for ", Attributable.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getAttribute(" + arg(0) + ").getBaseValue()";
    }
}