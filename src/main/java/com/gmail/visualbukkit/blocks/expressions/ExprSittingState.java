package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Sittable;

@Description("The sitting state of an entity that can sit")
public class ExprSittingState extends ExpressionBlock<Boolean> {

    public ExprSittingState() {
        init("sitting state of ", Sittable.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSitting()";
    }
}