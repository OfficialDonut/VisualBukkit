package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Drops an item stack at a location")
@Category(StatementCategory.WORLD)
public class StatDropItem extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("drop", ItemStack.class, "at", Location.class, new ChoiceParameter("naturally", "unnaturally"));
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().drop" + (arg(2).equals("naturally") ? "ItemNaturally" : "Item") + "(" + arg(1) + "," + arg(0) + ");";
    }
}
