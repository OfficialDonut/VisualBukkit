package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The seed of a world creator", "Changers: set", "Returns: number"})
public class ExprWorldCreatorSeed extends ChangeableExpressionBlock<Long> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("seed of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".seed()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".seed(" + delta + ");" : null;
    }
}
