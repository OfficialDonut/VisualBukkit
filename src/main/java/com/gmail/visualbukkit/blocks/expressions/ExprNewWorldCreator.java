package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Description("A new world creator")
public class ExprNewWorldCreator extends ExpressionBlock<WorldCreator> {

    public ExprNewWorldCreator() {
        init("world creator named ", String.class);
    }

    @Override
    public String toJava() {
        return "new WorldCreator(" + arg(0) + ")";
    }
}