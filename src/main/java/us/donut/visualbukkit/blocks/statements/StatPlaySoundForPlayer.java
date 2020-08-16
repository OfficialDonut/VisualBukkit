package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Plays a sound for a player")
@Category(StatementCategory.PLAYER)
public class StatPlaySoundForPlayer extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("play sound for player", Syntax.LINE_SEPARATOR,
                "sound:   ", Sound.class, Syntax.LINE_SEPARATOR,
                "player:  ", Player.class, Syntax.LINE_SEPARATOR,
                "location:", Location.class, Syntax.LINE_SEPARATOR,
                "volume:  ", float.class, Syntax.LINE_SEPARATOR,
                "pitch:   ", float.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".playSound(" + arg(2) + "," + arg(0) + "," + arg(3) + "," + arg(4) + ");";
    }
}
