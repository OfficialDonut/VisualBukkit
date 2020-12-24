package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Colors a string")
public class ExprColoredString extends ExpressionBlock<String> {

    public ExprColoredString() {
        init("color ", String.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.color(" + arg(0) + ")";
    }
}