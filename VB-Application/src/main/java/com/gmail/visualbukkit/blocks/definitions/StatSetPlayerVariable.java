package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetPlayerVariable extends Statement {

    public StatSetPlayerVariable() {
        super("stat-set-player-variable", "Set Player Variable", "VB", "Assigns a player variable");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Player", ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter("Var", ClassInfo.STRING), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.PLAYER_DATA);
            }

            @Override
            public String toJava() {
                return "PlayerDataManager.getInstance().setData(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
