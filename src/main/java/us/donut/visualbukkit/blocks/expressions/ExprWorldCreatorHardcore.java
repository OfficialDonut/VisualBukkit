package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The hardcore state of a world creator", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprWorldCreatorHardcore extends ModifiableExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hardcore state of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hardcore()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".hardcore(" + delta + ");" : null;
    }
}
