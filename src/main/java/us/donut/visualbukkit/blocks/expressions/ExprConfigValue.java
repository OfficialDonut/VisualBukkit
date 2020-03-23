package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.Configuration;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A value in a config", "Returns: object"})
public class ExprConfigValue extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("value of", String.class, "in", Configuration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(1) + ".set(" + arg(0) + "," + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }
}
