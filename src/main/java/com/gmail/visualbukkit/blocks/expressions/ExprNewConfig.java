package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.file.YamlConfiguration;

@Description("A new config")
public class ExprNewConfig extends ExpressionBlock<YamlConfiguration> {

    public ExprNewConfig() {
        init("new config");
    }

    @Override
    public String toJava() {
        return "new org.bukkit.configuration.file.YamlConfiguration()";
    }
}
