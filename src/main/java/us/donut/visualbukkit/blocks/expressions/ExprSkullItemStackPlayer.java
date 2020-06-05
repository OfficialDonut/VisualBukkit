package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
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
        return changeType == ChangeType.SET ? "setOwningPlayer(" + arg(0) + "," + delta + ");" : null;
    }

    @UtilMethod
    public static void setOwningPlayer(ItemStack item, OfflinePlayer player) {
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(player);
            item.setItemMeta(skullMeta);
        }
    }
}
