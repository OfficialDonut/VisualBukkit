package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The width of an entity", "Returns: number"})
public class ExprEntityWidth extends ExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("width of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWidth()";
    }
}
