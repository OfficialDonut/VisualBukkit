package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Makes a player run a command")
@Category(StatementCategory.PLAYER)
public class StatForceCommand extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("make", Player.class, "run", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".performCommand(" + arg(1) + ");";
    }
}
