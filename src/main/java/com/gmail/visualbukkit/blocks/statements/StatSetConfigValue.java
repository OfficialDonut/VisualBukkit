package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.Configuration;

@Description("Sets a value in a config")
public class StatSetConfigValue extends StatementBlock {

    public StatSetConfigValue() {
        init("set config value");
        initLine("config: ", Configuration.class);
        initLine("key:    ", String.class);
        initLine("value:  ", Object.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".set(" + arg(1) + "," + arg(2) + ");";
    }
}
