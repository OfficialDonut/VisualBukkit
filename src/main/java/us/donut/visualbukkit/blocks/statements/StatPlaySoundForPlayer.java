package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Plays a sound for a player")
public class StatPlaySoundForPlayer extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("play sound for player")
                .line("sound:   ", Sound.class)
                .line("player:  ", Player.class)
                .line("location:", Location.class)
                .line("volume:  ", float.class)
                .line("pitch:   ", float.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".playSound(" + arg(2) + "," + arg(0) + "," + arg(3) + "," + arg(4) + ");";
    }
}
