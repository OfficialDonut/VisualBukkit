package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Nameable;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The custom name of an entity", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprCustomName extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("custom name of", Nameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCustomName()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setCustomName(" + delta + ");" : null;
    }
}
