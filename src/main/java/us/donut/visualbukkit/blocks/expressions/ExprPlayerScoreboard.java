package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The scoreboard of a player", "Returns: scoreboard"})
@Modifier(ModificationType.SET)
public class ExprPlayerScoreboard extends ExpressionBlock<Scoreboard> {

    @Override
    protected Syntax init() {
        return new Syntax("scoreboard of", Player.class);
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
