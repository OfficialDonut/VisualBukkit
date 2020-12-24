package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Adds all elements from one list to another")
public class StatListAddAll extends StatementBlock {

    public StatListAddAll() {
        init("add all elements of ", List.class, " to ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addAll(" + arg(0) + ");";
    }
}
