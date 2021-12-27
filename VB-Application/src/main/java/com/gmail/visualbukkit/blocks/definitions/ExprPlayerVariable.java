package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class ExprPlayerVariable extends Expression {

    public ExprPlayerVariable() {
        super("expr-player-variable", "Player Variable", "VB", "The value of a player variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Player", ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter("Var", ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.PLAYER_DATA);
            }

            @Override
            public String toJava() {
                return "PlayerDataManager.getInstance().getData(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
