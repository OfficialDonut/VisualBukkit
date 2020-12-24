package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

@Category(Category.WORLD)
@Description("Sets the world type of a world creator")
public class StatSetWorldCreatorType extends StatementBlock {

    public StatSetWorldCreatorType() {
        init("set world type of ", WorldCreator.class, " to ", WorldType.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".type(" + arg(1) + ");";
    }
}
