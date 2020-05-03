package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.UUID;

@Name("Random UUID")
@Description({"A random UUID", "Returns: UUID"})
public class ExprRandomUUID extends ExpressionBlock<UUID> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("random UUID");
    }

    @Override
    public String toJava() {
        return "UUID.randomUUID()";
    }
}
