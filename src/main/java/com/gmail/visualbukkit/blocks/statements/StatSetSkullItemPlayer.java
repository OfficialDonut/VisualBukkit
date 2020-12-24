package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

@Category(Category.PLAYER)
@Description("Sets the player of a skull item")
public class StatSetSkullItemPlayer extends StatementBlock {

    public StatSetSkullItemPlayer() {
        init("set player of skull ", ItemStack.class, " to ", OfflinePlayer.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(SKULL_PLAYER_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setOwningPlayer(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String SKULL_PLAYER_METHOD =
            "public static void setOwningPlayer(ItemStack item, OfflinePlayer player) {\n" +
            "    SkullMeta skullMeta = (SkullMeta) item.getItemMeta();\n" +
            "    if (skullMeta != null) {\n" +
            "        skullMeta.setOwningPlayer(player);\n" +
            "        item.setItemMeta(skullMeta);\n" +
            "    }\n" +
            "}";
}
