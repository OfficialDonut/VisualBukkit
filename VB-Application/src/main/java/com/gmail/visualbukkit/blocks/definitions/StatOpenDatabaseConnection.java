package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class StatOpenDatabaseConnection extends Container {

    public StatOpenDatabaseConnection() {
        super("stat-open-database-connection");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.DATABASE);
            }

            @Override
            public String toJava() {
                return "try (Connection connection = DatabaseManager.getConnection()) {" + getChildJava() + "}";
            }
        };
    }
}
