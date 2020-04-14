package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The hardcore state of a world creator", "Returns: boolean"})
public class ExprWorldCreatorHardcore extends ChangeableExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hardcore state of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hardcore()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".hardcore(" + delta + ");" : null;
    }
}
