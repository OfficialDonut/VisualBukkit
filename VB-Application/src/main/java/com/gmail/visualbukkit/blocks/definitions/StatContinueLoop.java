package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.Statement;

public class StatContinueLoop extends Statement {

    public StatContinueLoop() {
        super("stat-continue-loop");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                if (checkForContainer("stat-list-loop") || checkForContainer("stat-number-loop") || checkForContainer("stat-while-loop")) {
                    setValid();
                } else {
                    setInvalid(String.format(VisualBukkitApp.getString("error.invalid_placement"), "Loop"));
                }
            }

            @Override
            public String toJava() {
                return "if (true) continue;";
            }
        };
    }
}
