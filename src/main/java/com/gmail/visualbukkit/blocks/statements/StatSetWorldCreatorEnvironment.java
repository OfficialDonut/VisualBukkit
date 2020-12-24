package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;
import org.bukkit.WorldCreator;

@Category(Category.WORLD)
@Description("Sets the world environment of a world creator")
public class StatSetWorldCreatorEnvironment extends StatementBlock {

    public StatSetWorldCreatorEnvironment() {
        init("set world environment of ", WorldCreator.class, " to ", World.Environment.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".environment(" + arg(1) + ");";
    }
}
