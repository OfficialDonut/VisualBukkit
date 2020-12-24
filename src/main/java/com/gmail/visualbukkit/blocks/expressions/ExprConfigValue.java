package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.Configuration;

@Description("A value in a config")
public class ExprConfigValue extends ExpressionBlock<Object> {

    public ExprConfigValue() {
        init("value of ", String.class, " in ", Configuration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }
}