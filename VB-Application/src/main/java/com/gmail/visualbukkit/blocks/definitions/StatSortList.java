package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSortList extends Statement {

    public StatSortList() {
        super("stat-sort-list");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "Collections.sort(" + arg(0) + ");";
            }
        };
    }
}
