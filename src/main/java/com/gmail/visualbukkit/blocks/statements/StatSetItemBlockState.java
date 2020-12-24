package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the block state associated with an item")
public class StatSetItemBlockState extends StatementBlock {

    public StatSetItemBlockState() {
        init("set block state of ", ItemStack.class, " to ", BlockState.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(BLOCK_STATE_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setBlockState(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String BLOCK_STATE_METHOD =
            "public static void setBlockState(ItemStack item, BlockState state) {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta instanceof BlockStateMeta) {\n" +
            "        state.update();\n" +
            "        ((BlockStateMeta) itemMeta).setBlockState(state);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
