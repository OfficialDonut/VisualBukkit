package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

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