package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Hides a player from another player")
@Category(StatementCategory.PLAYER)
public class StatHidePlayer extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("hide", Player.class, "from", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".hidePlayer(PluginMain.getInstance()," + arg(0) + ");";
    }
}
