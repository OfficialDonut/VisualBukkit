package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description("Un-hides a player from another player")
public class StatShowPlayer extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("show", Player.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".showPlayer(PluginMain.getInstance()," + arg(0) + ");";
    }
}
