package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Description("Loads a config from a file")
public class ExprConfigFromFile extends ExpressionBlock<YamlConfiguration> {

    public ExprConfigFromFile() {
        init("config from ", File.class);
    }

    @Override
    public String toJava() {
        return "org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(" + arg(0) + ")";
    }
}