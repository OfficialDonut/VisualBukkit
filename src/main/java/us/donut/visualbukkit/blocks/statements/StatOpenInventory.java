package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Opens an inventory to a player")
@Category(StatementCategory.PLAYER)
public class StatOpenInventory extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("open", Inventory.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".openInventory(" + arg(0) + ");";
    }
}
