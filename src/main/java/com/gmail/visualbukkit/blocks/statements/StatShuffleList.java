package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Randomly shuffles a list")
public class StatShuffleList extends StatementBlock {

    public StatShuffleList() {
        init("shuffle ", List.class);
    }

    @Override
    public String toJava() {
        return "Collections.shuffle(" + arg(0) + ");";
    }
}
