package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetPersistentVariable extends Statement {

    public StatSetPersistentVariable() {
        super("stat-set-persistent-variable", "Set Persistent Variable", "VB", "Assigns a persistent variable");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter("Var"), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.PERSISTENT_VARIABLES);
            }

            @Override
            public String toJava() {
                return "PluginMain.PERSISTENT_VARIABLES.set(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
