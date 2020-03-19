package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The name of a world", "Returns: string"})
public class ExprWorldName extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("name of", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
