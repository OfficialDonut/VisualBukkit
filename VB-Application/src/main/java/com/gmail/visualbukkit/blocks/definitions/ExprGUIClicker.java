package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClicker extends Expression {

    public ExprGUIClicker() {
        super("expr-gui-clicker", ClassInfo.of("org.bukkit.entity.Player"));
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-gui-click-handler");
            }

            @Override
            public String toJava() {
                return "((org.bukkit.entity.Player) event.getWhoClicked())";
            }
        };
    }
}
