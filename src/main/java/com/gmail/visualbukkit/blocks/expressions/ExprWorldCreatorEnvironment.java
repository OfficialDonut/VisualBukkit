package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;
import org.bukkit.WorldCreator;

@Description("The world environment of a world creator")
public class ExprWorldCreatorEnvironment extends ExpressionBlock<World.Environment> {

    public ExprWorldCreatorEnvironment() {
        init("world environment of ", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".environment()";
    }
}