package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The player of a skull item stack", "Changers: set", "Returns: offline player"})
public class ExprSkullItemStackPlayer extends ChangeableExpressionBlock<OfflinePlayer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("player of skull", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((SkullMeta)" + arg(0) + ".getItemMeta()).getOwningPlayer()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        if (changeType == ChangeType.SET) {
            String itemStackVar = randomVar();
            String itemMetaVar = randomVar();
            return "ItemStack " + itemStackVar + "=" + arg(0) + ";" +
                    "SkullMeta " + itemMetaVar + "=(SkullMeta)" + itemStackVar + ".getItemMeta();" +
                    itemMetaVar + ".setOwningPlayer(" + delta + ");" +
                    itemStackVar + ".setItemMeta(" + itemMetaVar + ");";
        }
        return null;
    }
}