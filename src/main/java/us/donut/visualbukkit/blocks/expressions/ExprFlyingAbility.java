package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The flying ability of a player", "Returns: boolean"})
public class ExprFlyingAbility extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("flying ability of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".getAllowFlight()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setAllowFlight(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return boolean.class;
    }
}
