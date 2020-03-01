package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The display name of a player", "Returns: string"})
public class ExprDisplayName extends ExpressionBlock implements ExpressionBlock.Changeable {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display name of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setDisplayName(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
