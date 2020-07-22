package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The seed of a world creator", "Returns: number"})
@Modifier(ModificationType.SET)
public class ExprWorldCreatorSeed extends ModifiableExpressionBlock<Long> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("seed of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".seed()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".seed(" + delta + ");" : null;
    }
}
