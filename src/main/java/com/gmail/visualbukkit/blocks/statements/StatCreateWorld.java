package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Category(Category.WORLD)
@Description("Creates a world")
public class StatCreateWorld extends StatementBlock {

    public StatCreateWorld() {
        init("create world with ", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".createWorld();";
    }
}
