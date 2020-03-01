package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The walk speed of a player", "Returns: number"})
public class ExprWalkSpeed extends ExpressionBlock implements ExpressionBlock.Changeable {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("walk speed of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWalkSpeed()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return arg(0) + ".setWalkSpeed(" + delta + ");";
        }
    }

    @Override
    public Class<?> getReturnType() {
        return float.class;
    }
}
