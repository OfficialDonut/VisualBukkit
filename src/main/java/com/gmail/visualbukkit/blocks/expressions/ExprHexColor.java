package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("A hex color code")
public class ExprHexColor extends ExpressionBlock<String> {

    public ExprHexColor() {
        init("hex color ", String.class);
    }

    @Override
    public String toJava() {
        return "net.md_5.bungee.api.ChatColor.of(" + arg(0) + ").toString()";
    }
}