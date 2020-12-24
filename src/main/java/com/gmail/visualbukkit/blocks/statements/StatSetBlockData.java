package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;

@Description("Sets the data of a block")
public class StatSetBlockData extends StatementBlock {

    public StatSetBlockData() {
        init("set data of ", Block.class, " to ", byte.class);
    }

    @Override
    public String toJava() {
        return "ReflectionUtil.getDeclaredMethod(" + arg(0) + ".getClass(), \"setData\", byte.class).invoke(" + arg(0) + "," + arg(1) + ");";
    }
}
