package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The operator status of a player", "Changers: set", "Returns: boolean"})
public class ExprOperatorStatus extends ChangeableExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("operator status of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isOp()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setOp(" + delta + ");" : null;
    }
}
