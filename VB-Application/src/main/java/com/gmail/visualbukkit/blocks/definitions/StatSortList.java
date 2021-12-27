package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSortList extends Statement {

    public StatSortList() {
        super("stat-sort-list", "Sort", "List", "Sorts the elements in a list (the elements must be comparable)");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("List", ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "Collections.sort(" + arg(0) + ");";
            }
        };
    }
}
