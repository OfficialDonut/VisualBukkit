package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSaveConfigToFile extends Statement {

    public StatSaveConfigToFile() {
        super("stat-save-config-to-file", "Save Config To File", "Config", "Saves a config to a file");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Config", ClassInfo.of("org.bukkit.configuration.file.FileConfiguration")), new ExpressionParameter("File", ClassInfo.of("java.io.File"))) {
            @Override
            public String toJava() {
                return "((org.bukkit.configuration.file.FileConfiguration)" + arg(0) + ").save(" + arg(1) + ");";
            }
        };
    }
}
