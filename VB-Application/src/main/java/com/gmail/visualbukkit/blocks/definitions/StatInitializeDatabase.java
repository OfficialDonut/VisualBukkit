package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatInitializeDatabase extends Statement {

    public StatInitializeDatabase() {
        super("stat-initialize-database");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.STRING)) {
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
