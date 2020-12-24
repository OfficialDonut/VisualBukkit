package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Category(Category.WORLD)
@Description("Sets the seed of a world creator")
public class StatSetWorldCreatorSeed extends StatementBlock {

    public StatSetWorldCreatorSeed() {
        init("set seed of ", WorldCreator.class, " to ", long.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".seed(" + arg(1) + ");";
    }
}
