package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The prefix of a scoreboard team", "Changers: set", "Returns: string"})
public class ExprTeamPrefix extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("prefix of", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPrefix()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setPrefix(PluginMain.color(" + delta + "));" : null;
    }
}
