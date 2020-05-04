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
        return new SyntaxNode("play", Sound.class, "for", Player.class, "with volume", float.class, "and pitch", float.class, "at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".playSound(" + arg(4) + "," + arg(0) + "," + arg(2) + "," + arg(3) + ");";
    }
}
