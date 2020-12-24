package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Description("The compass target of a player")
public class ExprCompassTarget extends ExpressionBlock<Location> {

    public ExprCompassTarget() {
        init("compass target of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCompassTarget()";
    }
}