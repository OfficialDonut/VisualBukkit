package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Category("Player")
@Description({"The players in a world", "Returns: list of players"})
public class ExprPlayersInWorld extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("players in", World.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getPlayers())";
    }
}
