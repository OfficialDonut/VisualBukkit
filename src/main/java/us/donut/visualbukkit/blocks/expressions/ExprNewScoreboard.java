package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Scoreboard;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A new scoreboard", "Returns: scoreboard"})
public class ExprNewScoreboard extends ExpressionBlock<Scoreboard> {

    @Override
    protected Syntax init() {
        return new Syntax("new scoreboard");
    }

    @Override
    public String toJava() {
        return "Bukkit.getScoreboardManager().getNewScoreboard()";
    }
}
