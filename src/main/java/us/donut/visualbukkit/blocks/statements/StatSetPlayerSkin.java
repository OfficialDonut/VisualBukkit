package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description("Sets the skin of a player (must be used in PlayerLoginEvent)")
@Category(StatementCategory.PLAYER)
public class StatSetPlayerSkin extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("set player skin", Syntax.LINE_SEPARATOR,
                "player:    ", Player.class, Syntax.LINE_SEPARATOR,
                "skin value:", String.class, Syntax.LINE_SEPARATOR,
                "signature: ", String.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateEvent(PlayerLoginEvent.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
        BuildContext.addUtilMethod(UtilMethod.SET_SKIN);
        return "UtilMethods.setSkin(" + arg(0) + "," + arg(1) + "," + arg(2) + ");";
    }
}
