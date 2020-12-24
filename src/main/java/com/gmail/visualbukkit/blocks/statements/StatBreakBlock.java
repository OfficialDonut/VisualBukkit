package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;

@Category(Category.WORLD)
@Description("Breaks a block and spawns drops")
public class StatBreakBlock extends StatementBlock {

    public StatBreakBlock() {
        init("naturally break ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".breakNaturally();";
    }
}
