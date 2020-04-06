package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The score of an entry for a scoreboard objective", "Returns: number"})
public class ExprObjectiveScore extends ChangeableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("score of entry", String.class, "for", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getScore(" + arg(0) + ").getScore()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(1) + ".getScore(" + arg(0) + ").setScore(" + delta + ");" : null;
    }
}
