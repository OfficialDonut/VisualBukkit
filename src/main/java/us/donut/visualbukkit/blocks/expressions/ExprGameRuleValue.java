package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The value of a game rule in a world", "Changers: set", "Returns: object"})
public class ExprGameRuleValue extends ChangeableExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("value of game rule", String.class, "in", World.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getGameRuleValue(GameRule.getByName(" + arg(0) + "))";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(1) + ".setGameRule(GameRule.getByName(" + arg(0) + ")," + delta + ");" : null;
    }
}
