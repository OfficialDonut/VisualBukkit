package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.modules.classes.Duration;

@Description("Sends a title to a player")
@Category(StatementCategory.PLAYER)
public class StatSendTitle extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("send title to player", Syntax.LINE_SEPARATOR,
                "title:   ", String.class, Syntax.LINE_SEPARATOR,
                "subtitle:", String.class, Syntax.LINE_SEPARATOR,
                "player:  ", Player.class, Syntax.LINE_SEPARATOR,
                "fade in: ", Duration.class, Syntax.LINE_SEPARATOR,
                "stay for:", Duration.class, Syntax.LINE_SEPARATOR,
                "fade out:", Duration.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".sendTitle(" + arg(0) + "," + arg(1) + ",(int)" +
                arg(3) + ".getTicks(),(int)" + arg(4) + ".getTicks(),(int)" + arg(5) + ".getTicks());";
    }
}
