package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;

public class StatReloadPluginConfig extends Statement {

    public StatReloadPluginConfig() {
        super("stat-reload-plugin-config", "Reload Config", "Bukkit", "Reloads the default plugin config");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().reloadConfig();";
            }
        };
    }
}
