package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The scoreboard of a player", "Returns: scoreboard"})
@Modifier(ModificationType.SET)
public class ExprPlayerScoreboard extends ModifiableExpressionBlock<Scoreboard> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("scoreboard of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getScoreboard()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setScoreboard(" + delta + ");" : null;
    }
}
