package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Closes a player's inventory")
@Category(StatementCategory.PLAYER)
public class StatCloseInventory extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("close inventory of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".closeInventory();";
    }
}
