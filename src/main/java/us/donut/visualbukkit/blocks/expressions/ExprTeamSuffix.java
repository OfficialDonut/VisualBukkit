package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The suffix of a scoreboard team", "Returns: string"})
public class ExprTeamSuffix extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("suffix of", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSuffix()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setSuffix(color(" + delta + "));" : null;
    }
}
