package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The flying state of a player", "Returns: boolean"})
public class ExprFlyingState extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("flying state of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isFlying()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setFlying(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return boolean.class;
    }
}
