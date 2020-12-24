package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;

@Category(Category.WORLD)
@Description("Sets the pitch of a location")
public class StatSetLocationPitch extends StatementBlock {

    public StatSetLocationPitch() {
        init("set pitch of ", Location.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setPitch(" + arg(1) + ");";
    }
}
