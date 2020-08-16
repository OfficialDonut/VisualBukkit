package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Registers a new scoreboard objective", "Returns: objective"})
public class ExprNewScoreboardObjective extends ExpressionBlock<Objective> {

    @Override
    protected Syntax init() {
        return new Syntax("new objective named", String.class, "with criteria", String.class, "for", Scoreboard.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".registerNewObjective(" + arg(0) + "," + arg(1) + ")";
    }
}
