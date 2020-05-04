package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The display name of a scoreboard team", "Changers: set", "Returns: string"})
public class ExprTeamDisplayName extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display name of", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setDisplayName(PluginMain.color(" + delta + "));" : null;
    }
}
