package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The display slot of a scoreboard objective", "Returns: display slot"})
public class ExprObjectiveDisplaySlot extends ChangeableExpressionBlock<DisplaySlot> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display slot of", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplaySlot()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setDisplaySlot(" + delta + ");" : null;
    }
}
