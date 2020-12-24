package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.file.FileConfiguration;

@Description("A string representation of a config")
public class ExprConfigToString extends ExpressionBlock<String> {

    public ExprConfigToString() {
        init(FileConfiguration.class, " to string");
    }

    @Override
    public String toJava() {
        return arg(0) + ".saveToString()";
    }
}