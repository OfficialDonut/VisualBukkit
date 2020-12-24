package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Description("The hardcore state of a world creator")
public class ExprWorldCreatorHardcore extends ExpressionBlock<Boolean> {

    public ExprWorldCreatorHardcore() {
        init("hardcore state of ", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hardcore()";
    }
}