package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCreatedGUIPlayer extends Expression {

    public ExprCreatedGUIPlayer() {
        super("expr-created-gui-player", "Created GUI Player", "GUI", "The player for which the GUI is being created");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.entity.Player");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompCreateGUI.class);
            }

            @Override
            public String toJava() {
                return "guiPlayer";
            }
        };
    }
}
