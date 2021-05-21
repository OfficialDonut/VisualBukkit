package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatOpenGUI extends Statement {

    public StatOpenGUI() {
        super("stat-open-gui");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player"))) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.GUI);
            }

            @Override
            public String toJava() {
                return "GUIManager.getInstance().open(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
