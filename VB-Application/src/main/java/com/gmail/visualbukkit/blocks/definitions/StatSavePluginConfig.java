package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;

public class StatSavePluginConfig extends Statement {

    public StatSavePluginConfig() {
        super("stat-save-plugin-config", "Save Plugin Config", "Bukkit", "Saves the default plugin config");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().saveConfig();";
            }
        };
    }
}
