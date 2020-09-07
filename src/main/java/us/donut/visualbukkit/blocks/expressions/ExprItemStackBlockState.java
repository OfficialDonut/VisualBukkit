package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"The block state associated with an item stack", "Returns: block state"})
@Modifier(ModificationType.SET)
public class ExprItemStackBlockState extends ExpressionBlock<BlockState> {

    @Override
    protected Syntax init() {
        return new Syntax("block state of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((BlockStateMeta)" + arg(0) + ".getItemMeta()).getBlockState()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod(UtilMethod.SET_ITEM_BLOCK_STATE);
        return modificationType == ModificationType.SET ? "UtilMethods.setItemBlockState(" + arg(0) + "," + delta + ");" : null;
    }
}
