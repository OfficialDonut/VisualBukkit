package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Adds an object to a list")
public class StatRemoveFromList extends StatementBlock {

    public StatRemoveFromList() {
        init("remove ", Object.class, " from ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".remove(" + arg(0) + ");";
    }
}
