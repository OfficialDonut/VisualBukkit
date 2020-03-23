package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The displayed player list header for a player", "Returns: string"})
public class ExprPlayerListHeader extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("player list header of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayerListHeader()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setPlayerListHeader(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
