package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Description("Opens a connection to a database")
public class StatOpenDatabaseConnection extends ParentBlock {

    public StatOpenDatabaseConnection() {
        init("open database connection");
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.DATABASE);
    }

    @Override
    public String toJava() {
        return "try (Connection connection = DatabaseManager.getConnection()) {" + getChildJava() + "}";
    }
}
