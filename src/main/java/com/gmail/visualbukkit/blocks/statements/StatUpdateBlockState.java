package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.BlockState;

@Description("Updates the state of a block")
public class StatUpdateBlockState extends StatementBlock {

    public StatUpdateBlockState() {
        init("update ", BlockState.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".update();";
    }
}
