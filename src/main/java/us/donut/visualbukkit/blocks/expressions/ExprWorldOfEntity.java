package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The world of an entity", "Returns: world"})
public class ExprWorldOfEntity extends ExpressionBlock<World> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("world of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld()";
    }
}
