package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@Description("All of the keys in a config")
@SuppressWarnings("rawtypes")
public class ExprConfigKeys extends ExpressionBlock<List> {

    public ExprConfigKeys() {
        init("keys of ", ConfigurationSection.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getKeys(false))";
    }
}