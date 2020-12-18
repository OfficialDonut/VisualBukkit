package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Adds an object to a list")
public class StatAddToList extends StatementBlock {

    public StatAddToList() {
        init("add ", Object.class, " to ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".add(" + arg(0) + ");";
    }
}
