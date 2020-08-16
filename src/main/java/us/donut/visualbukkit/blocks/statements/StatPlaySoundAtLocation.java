package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Sound;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Plays a sound at a location")
@Category(StatementCategory.WORLD)
public class StatPlaySoundAtLocation extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("play sound at location", Syntax.LINE_SEPARATOR,
                "sound:   ", Sound.class, Syntax.LINE_SEPARATOR,
                "location:", Location.class, Syntax.LINE_SEPARATOR,
                "volume:  ", float.class, Syntax.LINE_SEPARATOR,
                "pitch:   ", float.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().playSound(" + arg(1) + "," + arg(0) + "," + arg(2) + "," + arg(3) + ");";
    }
}
