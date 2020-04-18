package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Nameable;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The custom name of an entity", "Changers: set", "Returns: string"})
public class ExprCustomName extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("custom name of", Nameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCustomName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setCustomName(" + delta + ");" : null;
    }
}
