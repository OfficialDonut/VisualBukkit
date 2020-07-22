package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The score of an entry for a scoreboard objective", "Returns: number"})
@Modifier(ModificationType.SET)
public class ExprObjectiveScore extends ModifiableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("score of entry", String.class, "for", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getScore(" + arg(0) + ").getScore()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".getScore(" + arg(0) + ").setScore(" + delta + ");" : null;
    }
}
