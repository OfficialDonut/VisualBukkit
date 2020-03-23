package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The sneaking state of a player", "Returns: boolean"})
public class ExprSneakingState extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("sneaking state of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSneaking()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setSneaking(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return boolean.class;
    }
}
