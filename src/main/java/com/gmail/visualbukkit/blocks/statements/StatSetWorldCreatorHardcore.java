package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;

@Category(Category.WORLD)
@Description("Sets the hardcore state of a world creator")
public class StatSetWorldCreatorHardcore extends StatementBlock {

    public StatSetWorldCreatorHardcore() {
        init("set hardcore state of ", WorldCreator.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hardcore(" + arg(1) + ");";
    }
}
