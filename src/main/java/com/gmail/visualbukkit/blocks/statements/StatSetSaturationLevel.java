package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the saturation level of a player")
public class StatSetSaturationLevel extends StatementBlock {

    public StatSetSaturationLevel() {
        init("set saturation level of ", Player.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSaturation(" + arg(1) + ");";
    }
}
