package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The height of an entity", "Returns: number"})
public class ExprEntityHeight extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("height of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHeight()";
    }

    @Override
    public Class<?> getReturnType() {
        return double.class;
    }
}
