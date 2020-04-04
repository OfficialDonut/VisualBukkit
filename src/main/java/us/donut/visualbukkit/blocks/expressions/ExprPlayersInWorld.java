package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Player")
@Description({"The players in a world", "Returns: list of players"})
public class ExprPlayersInWorld extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("players in", World.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getPlayers())";
    }
}
