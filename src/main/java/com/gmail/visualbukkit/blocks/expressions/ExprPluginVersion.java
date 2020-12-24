package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The version of a plugin")
public class ExprPluginVersion extends ExpressionBlock<String> {

    public ExprPluginVersion() {
        init("version of plugin ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getPluginManager().getPlugin(" + arg(0) + ").getDescription().getVersion()";
    }
}