package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A vector", "Returns: vector"})
public class ExprVector extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("vector(", double.class, ",", double.class, ",", double.class, ")");
    }

    @Override
    public String toJava() {
        return "new Vector(" + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return Vector.class;
    }
}
