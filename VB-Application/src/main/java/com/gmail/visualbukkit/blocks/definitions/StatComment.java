package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class StatComment extends Statement {

    public StatComment() {
        super("stat-comment");
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, new InputParameter()) {
            @Override
            public String toJava() {
                return "";
            }
        };

        block.getBody().setOpacity(0.75);
        block.getBody().opacityProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == null || newValue.doubleValue() != 0.75) {
                block.setOpacity(0.75);
            }
        });

        return block;
    }
}
