package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The fly speed of a player", "Returns: number"})
public class ExprFlySpeed extends ExpressionBlock implements ExpressionBlock.Changeable {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("fly speed of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFlySpeed()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return arg(0) + ".setFlySpeed(" + delta + ");";
        }
    }

    @Override
    public Class<?> getReturnType() {
        return float.class;
    }
}
