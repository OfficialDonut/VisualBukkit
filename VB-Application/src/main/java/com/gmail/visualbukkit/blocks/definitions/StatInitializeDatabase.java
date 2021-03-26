package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class StatInitializeDatabase extends Statement {

    public StatInitializeDatabase() {
        super("stat-initialize-database");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(String.class), new ExpressionParameter(String.class), new ExpressionParameter(String.class)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.DATABASE);
            }

            @Override
            public String toJava() {
                return "DatabaseManager.init(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
