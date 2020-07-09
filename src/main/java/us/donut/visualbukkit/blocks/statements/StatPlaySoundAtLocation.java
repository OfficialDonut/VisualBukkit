package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Sound;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Plays a sound at a location")
public class StatPlaySoundAtLocation extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("play sound at location")
                .line("sound:   ", Sound.class)
                .line("location:", Location.class)
                .line("volume:  ", float.class)
                .line("pitch:   ", float.class);
    }

    @Override
    public String toJava() {
        return "playSound(" + arg(0) + "," + arg(1) + "," + arg(2) + "," + arg(3) + ");";
    }

    @UtilMethod
    public static void playSound(Sound sound, Location loc, float vol, float pitch) {
        loc.getWorld().playSound(loc, sound, vol, pitch);
    }
}
