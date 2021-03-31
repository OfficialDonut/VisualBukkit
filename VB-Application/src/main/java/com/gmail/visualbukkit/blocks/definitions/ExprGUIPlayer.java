package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIPlayer extends Expression {

    public ExprGUIPlayer() {
        super("expr-gui-player", ClassInfo.of("org.bukkit.entity.Player"));
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
