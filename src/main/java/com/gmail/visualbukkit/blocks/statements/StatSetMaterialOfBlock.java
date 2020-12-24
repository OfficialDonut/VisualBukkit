package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Category(Category.WORLD)
@Description("Sets the material of a block")
public class StatSetMaterialOfBlock extends StatementBlock {

    public StatSetMaterialOfBlock() {
        init("set material of ", Block.class, " to ", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setType(" + arg(1) + ");";
    }
}
