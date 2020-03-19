package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Drops an item stack at a location")
public class StatDropItem extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("drop", ItemStack.class, "at", Location.class, new ChoiceParameter("naturally", "unnaturally"));
    }

    @Override
    public String toJava() {
        String method = arg(2).equals("naturally") ? "dropItem" : "dropItemNaturally";
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(1) + ";" +
                locVar + ".getWorld()." + method + "(" + locVar + "," + arg(0) + ");";
    }
}
