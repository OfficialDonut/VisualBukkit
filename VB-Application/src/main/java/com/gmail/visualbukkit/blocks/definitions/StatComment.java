package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class StatComment extends Statement {

    public StatComment() {
        super("stat-comment", "Comment", "VB", "A comment which has no effect");
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, new InputParameter("//")) {
            @Override
            public String toJava() {
                return "";
            }
        };
        block.getBody().setOpacity(0.5);
        return block;
    }
}
