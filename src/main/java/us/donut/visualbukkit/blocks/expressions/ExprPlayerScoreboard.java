package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The scoreboard of a player", "Changers: set", "Returns: scoreboard"})
public class ExprPlayerScoreboard extends ChangeableExpressionBlock<Scoreboard> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("scoreboard of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getScoreboard()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setScoreboard(" + delta + ");" : null;
    }
}
