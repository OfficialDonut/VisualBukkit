package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

@Description("The world type of a world creator")
public class ExprWorldCreatorType extends ExpressionBlock<WorldType> {

    public ExprWorldCreatorType() {
        init("world type of ", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".type()";
    }
}