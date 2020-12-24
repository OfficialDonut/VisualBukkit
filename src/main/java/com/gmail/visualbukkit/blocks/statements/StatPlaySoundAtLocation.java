package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.Sound;

@Category(Category.WORLD)
@Description("Plays a sound at a location")
public class StatPlaySoundAtLocation extends StatementBlock {

    public StatPlaySoundAtLocation() {
        init("play sound at location");
        initLine("sound:    ", Sound.class);
        initLine("location: ", Location.class);
        initLine("volume:   ", float.class);
        initLine("pitch:    ", float.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().playSound(" + arg(1) + "," + arg(0) + "," + arg(2) + "," + arg(3) + ");";
    }
}
