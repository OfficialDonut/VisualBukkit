package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;

@Description("The location to which an end crystal points")
public class ExprEndCrystalBeamTarget extends ExpressionBlock<Location> {

    public ExprEndCrystalBeamTarget() {
        init("beam target of ", EnderCrystal.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBeamTarget()";
    }
}
