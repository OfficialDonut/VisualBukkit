package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description("Kicks a player from the server")
public class StatKickPlayer extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("kick", Player.class, "for", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".kickPlayer(PluginMain.color(" + arg(1) + "));";
    }
}
