package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Removes the element at an index in a list")
public class StatRemoveListIndex extends StatementBlock {

    public StatRemoveListIndex() {
        init("remove index ", int.class, " from ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".remove(" + arg(0) + ");";
    }
}
