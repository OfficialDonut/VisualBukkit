package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Sets the element at an index in a list")
public class StatSetListIndex extends StatementBlock {

    public StatSetListIndex() {
        init("set list index");
        initLine("list:  ", List.class);
        initLine("index: ", int.class);
        initLine("value  ", Object.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".set(" + arg(1) + "," + arg(2) + ");";
    }
}
