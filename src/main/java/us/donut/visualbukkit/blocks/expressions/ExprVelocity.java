package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The velocity of an entity", "Returns: vector"})
@Modifier(ModificationType.SET)
public class ExprVelocity extends ModifiableExpressionBlock<Vector> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("velocity of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVelocity()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setVelocity(" + delta + ");" : null;
    }
}
