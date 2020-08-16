package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Un-hides a player from another player")
@Category(StatementCategory.PLAYER)
public class StatShowPlayer extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("show", Player.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".showPlayer(PluginMain.getInstance()," + arg(0) + ");";
    }
}
