package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The velocity of an entity", "Changers: set", "Returns: vector"})
public class ExprVelocity extends ChangeableExpressionBlock<Vector> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("velocity of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVelocity()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setVelocity(" + delta + ");" : null;
    }
}
