package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a plugin is enabled")
public class ExprIsPluginEnabled extends ExpressionBlock<Boolean> {

    public ExprIsPluginEnabled() {
        init("plugin ", String.class, " is enabled");
    }

    @Override
    public String toJava() {
        return "Bukkit.getPluginManager().isPluginEnabled(" + arg(0) + ")";
    }
}