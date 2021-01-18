package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Removes all elements in one list from another")
public class StatListRemoveAll extends StatementBlock {

    public StatListRemoveAll() {
        init("remove all elements of ", List.class, " from ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removeAll(" + arg(0) + ");";
    }
}
