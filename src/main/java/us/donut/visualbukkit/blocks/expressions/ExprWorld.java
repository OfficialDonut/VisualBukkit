package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A world on the server", "Returns: world"})
public class ExprWorld extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("world named", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getWorld(" + arg(0) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return World.class;
    }
}
