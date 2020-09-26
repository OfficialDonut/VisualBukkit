package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Sign;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A line of a sign", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprSignLine extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("line", int.class, "of", Sign.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getLine(" + arg(0) + "-1)";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".setLine(" + arg(0) + "-1," + delta + ");" : null;
    }
}
