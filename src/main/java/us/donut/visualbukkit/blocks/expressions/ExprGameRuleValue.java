package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The value of a game rule in a world", "Returns: object"})
@Modifier(ModificationType.SET)
public class ExprGameRuleValue extends ModifiableExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("value of game rule", String.class, "in", World.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getGameRuleValue(GameRule.getByName(" + arg(0) + "))";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".setGameRule(GameRule.getByName(" + arg(0) + ")," + delta + ");" : null;
    }
}
