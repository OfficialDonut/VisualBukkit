package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSaveConfigToFile extends Statement {

    public StatSaveConfigToFile() {
        super("stat-save-config-to-file");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.configuration.ConfigurationSection")), new ExpressionParameter(ClassInfo.of("java.io.File"))) {
            @Override
            public String toJava() {
                return "((org.bukkit.configuration.file.FileConfiguration)" + arg(0) + ").save(" + arg(1) + ");";
            }
        };
    }
}
