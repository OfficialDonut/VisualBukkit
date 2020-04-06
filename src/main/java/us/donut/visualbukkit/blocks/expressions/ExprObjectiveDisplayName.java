package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The display name of a scoreboard objective", "Returns: string"})
public class ExprObjectiveDisplayName extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display name of", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setDisplayName(color(" + delta + "));" : null;
    }
}
