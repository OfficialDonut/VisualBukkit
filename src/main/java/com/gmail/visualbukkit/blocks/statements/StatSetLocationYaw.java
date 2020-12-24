package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;

@Category(Category.WORLD)
@Description("Sets the yaw of a location")
public class StatSetLocationYaw extends StatementBlock {

    public StatSetLocationYaw() {
        init("set yaw of ", Location.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setYaw(" + arg(1) + ");";
    }
}
