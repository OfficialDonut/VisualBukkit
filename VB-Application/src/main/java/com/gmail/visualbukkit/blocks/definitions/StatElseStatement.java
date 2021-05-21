package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.ui.LanguageManager;

public class StatElseStatement extends Container {

    public StatElseStatement() {
        super("stat-else-statement");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                if (checkForPrevious("stat-if-statement") || checkForPrevious("stat-else-if-statement")) {
                    setValid();
                } else {
                    setInvalid(LanguageManager.get("error.invalid_else"));
                }
            }

            @Override
            public String toJava() {
                return "else {" + getChildJava() + "}";
            }
        };
    }
}
