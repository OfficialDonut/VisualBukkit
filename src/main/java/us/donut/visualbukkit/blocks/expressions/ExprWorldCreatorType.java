package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The world type of a world creator", "Returns: WorldType"})
public class ExprWorldCreatorType extends ChangeableExpressionBlock<WorldType> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("world type of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".type()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".type(" + delta + ");" : null;
    }
}
