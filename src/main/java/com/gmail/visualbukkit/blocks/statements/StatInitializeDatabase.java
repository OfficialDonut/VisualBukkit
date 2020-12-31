package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Description("Initializes a database for connections")
public class StatInitializeDatabase extends StatementBlock {

    public StatInitializeDatabase() {
        init("initialize database");
        initLine("url:      ", String.class);
        initLine("username: ", String.class);
        initLine("password: ", String.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.DATABASE);
    }

    @Override
    public String toJava() {
        return "DatabaseManager.init(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
