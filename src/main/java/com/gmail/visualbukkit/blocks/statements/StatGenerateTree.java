package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.TreeType;

@Category(Category.WORLD)
@Description("Generates a tree at a location")
public class StatGenerateTree extends StatementBlock {

    public StatGenerateTree() {
        init("generate ", TreeType.class, " tree at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().generateTree(" + arg(1) + "," + arg(0) + ");";
    }
}
