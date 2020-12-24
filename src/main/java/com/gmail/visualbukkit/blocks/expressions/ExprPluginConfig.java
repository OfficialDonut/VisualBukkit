package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.file.FileConfiguration;

@Description("The plugin config")
public class ExprPluginConfig extends ExpressionBlock<FileConfiguration> {

    public ExprPluginConfig() {
        init("plugin config");
    }

    @Override
    public String toJava() {
        return "PluginMain.getInstance().getConfig()";
    }
}