package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.IO)
@Description("Reloads the plugin config")
public class StatReloadPluginConfig extends StatementBlock {

    public StatReloadPluginConfig() {
        init("reload plugin config");
    }

    @Override
    public String toJava() {
        return "PluginMain.getInstance().reloadConfig();";
    }
}
