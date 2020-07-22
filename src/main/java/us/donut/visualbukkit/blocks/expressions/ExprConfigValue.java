package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.Configuration;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A value in a config", "Returns: object"})
@Modifier(ModificationType.SET)
public class ExprConfigValue extends ModifiableExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("value of", String.class, "in", Configuration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".set(" + arg(0) + "," + delta + ");" : null;
    }
}
