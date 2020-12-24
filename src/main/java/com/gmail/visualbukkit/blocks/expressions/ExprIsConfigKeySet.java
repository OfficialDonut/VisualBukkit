package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.Configuration;

@Description("Checks if a config value is set")
public class ExprIsConfigKeySet extends ExpressionBlock<Boolean> {

    public ExprIsConfigKeySet() {
        init(String.class, " is set in ", Configuration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".isSet(" + arg(0) + ")";
    }
}