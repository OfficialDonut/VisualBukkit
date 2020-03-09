package us.donut.visualbukkit.blocks.statements;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description("Clears an inventory")
public class StatClearInventory extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clear", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".clear();";
    }
}
