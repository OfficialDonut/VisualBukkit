package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.entity.Player;

public class StatOpenGUI extends Statement {

    public StatOpenGUI() {
        super("stat-open-gui");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(String.class), new ExpressionParameter(Player.class)) {
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
