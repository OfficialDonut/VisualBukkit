package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"The player of a skull item stack", "Returns: offline player"})
@Modifier(ModificationType.SET)
public class ExprSkullItemStackPlayer extends ExpressionBlock<OfflinePlayer> {

    @Override
    protected Syntax init() {
        return new Syntax("player of skull", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((SkullMeta)" + arg(0) + ".getItemMeta()).getOwningPlayer()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod(UtilMethod.SET_OWNING_PLAYER);
        return modificationType == ModificationType.SET ? "UtilMethods.setOwningPlayer(" + arg(0) + "," + delta + ");" : null;
    }
}
