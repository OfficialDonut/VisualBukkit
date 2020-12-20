package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Sorts a list (its elements must be comparable)")
public class StatSortList extends StatementBlock {

    public StatSortList() {
        init("sort ", List.class);
    }

    @Override
    public String toJava() {
        return "Collections.sort(" + arg(0) + ");";
    }
}
