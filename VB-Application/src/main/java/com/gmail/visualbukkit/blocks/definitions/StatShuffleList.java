package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatShuffleList extends Statement {

    public StatShuffleList() {
        super("stat-shuffle-list", "Shuffle", "List", "Shuffles the elements in a list");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("List", ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "Collections.shuffle(" + arg(0) + ");";
            }
        };
    }
}
