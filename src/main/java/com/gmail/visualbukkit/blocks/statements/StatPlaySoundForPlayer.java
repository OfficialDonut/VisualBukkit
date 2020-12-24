package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Plays a sound for a player")
public class StatPlaySoundForPlayer extends StatementBlock {

    public StatPlaySoundForPlayer() {
        init("play sound for player");
        initLine("sound:    ", Sound.class);
        initLine("player:   ", Player.class);
        initLine("location: ", Location.class);
        initLine("volume:   ", float.class);
        initLine("pitch:    ", float.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".playSound(" + arg(2) + "," + arg(0) + "," + arg(3) + "," + arg(4) + ");";
    }
}
