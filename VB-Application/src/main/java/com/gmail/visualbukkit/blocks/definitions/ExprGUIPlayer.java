package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.entity.Player;

public class ExprGUIPlayer extends Expression {

    public ExprGUIPlayer() {
        super("expr-gui-player", Player.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-create-gui");
            }

            @Override
            public String toJava() {
                return "guiPlayer";
            }
        };
    }
}
