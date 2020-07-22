package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The world environment of a world creator", "Returns: environment"})
@Modifier(ModificationType.SET)
public class ExprWorldCreatorEnvironment extends ModifiableExpressionBlock<World.Environment> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("world environment of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".environment()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".environment(" + delta + ");" : null;
    }
}
