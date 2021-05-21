package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.ui.LanguageManager;

public class StatStopLoop extends Statement {

    public StatStopLoop() {
        super("stat-stop-loop");
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
                    setInvalid(String.format(LanguageManager.get("error.invalid_block_parent"), "Loop"));
                }
            }

            @Override
            public String toJava() {
                return "if (true) break;";
            }
        };
    }
}
