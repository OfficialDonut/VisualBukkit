package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Registers a new scoreboard team", "Returns: team"})
public class ExprNewScoreboardTeam extends ExpressionBlock<Team> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("new team named", String.class, "for", Scoreboard.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".registerNewTeam(" + arg(0) + ")";
    }
}
