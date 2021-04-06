package com.gmail.visualbukkit.extensions.papi;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprPlaceholderPlayer extends Expression {

    public ExprPlaceholderPlayer() {
        super("expr-placeholder-player", ClassInfo.of("org.bukkit.entity.Player"));
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-register-placeholder");
            }

            @Override
            public String toJava() {
                return "placeholderPlayer";
            }
        };
    }
}
