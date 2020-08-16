package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A custom string", "Returns: string"})
public class ExprString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        return new Syntax(new StringLiteralParameter());
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
