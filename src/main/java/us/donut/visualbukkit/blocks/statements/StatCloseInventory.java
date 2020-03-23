package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.HumanEntity;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description("Closes a human entity's inventory")
public class StatCloseInventory extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("close inventory of", HumanEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".closeInventory();";
    }
}
