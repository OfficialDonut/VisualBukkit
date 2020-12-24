package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.ConfigurationSection;

@Description("A section of a config")
public class ExprConfigSection extends ExpressionBlock<ConfigurationSection> {

    public ExprConfigSection() {
        init("section ", String.class, " of ", ConfigurationSection.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getConfigurationSection(" + arg(0) + ")";
    }
}