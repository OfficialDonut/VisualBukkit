package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Name("Open GUI")
@Description("Opens a GUI to a player")
public class StatOpenGUI extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("open GUI", new StringLiteralParameter(), "to", Player.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.GUI);
        return "GuiManager.getInstance().open(" + arg(0) + "," + arg(1) + ");";
    }
}
