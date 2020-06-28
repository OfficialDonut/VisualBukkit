package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Gives an item stack to a player")
public class StatGiveItemStack extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("give", ItemStack.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getInventory().addItem(new ItemStack[]{" + arg(0) + "});";
    }
}
