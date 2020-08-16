package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.UUID;

@Name("Random UUID")
@Description({"A random UUID", "Returns: UUID"})
public class ExprRandomUUID extends ExpressionBlock<UUID> {

    @Override
    protected Syntax init() {
        return new Syntax("random UUID");
    }

    @Override
    public String toJava() {
        return "UUID.randomUUID()";
    }
}
