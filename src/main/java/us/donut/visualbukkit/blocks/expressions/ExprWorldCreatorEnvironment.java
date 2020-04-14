package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The world environment of a world creator", "Returns: environment"})
public class ExprWorldCreatorEnvironment extends ChangeableExpressionBlock<World.Environment> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("world environment of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".environment()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".environment(" + delta + ");" : null;
    }
}
