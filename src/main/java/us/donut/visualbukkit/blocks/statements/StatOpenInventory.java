package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Opens an inventory to a human entity")
public class StatOpenInventory extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("open", Inventory.class, "to", HumanEntity.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".openInventory(" + arg(0) + ");";
    }
}
