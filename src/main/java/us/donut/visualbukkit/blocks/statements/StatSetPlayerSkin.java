package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Sets the skin of a player (must be used in PlayerLoginEvent)")
public class StatSetPlayerSkin extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("set player skin")
                .line("player:    ", Player.class)
                .line("skin value:", String.class)
                .line("signature: ", String.class);
    }

    @Override
    public String toJava() {
        if (PlayerLoginEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
            BuildContext.addUtilMethod("setSkin");
            return "UtilMethods.setSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
        }
        throw new IllegalStateException();
    }
}
