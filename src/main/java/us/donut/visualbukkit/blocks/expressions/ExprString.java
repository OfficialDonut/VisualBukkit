package us.donut.visualbukkit.blocks.expressions;

import org.apache.commons.lang.StringEscapeUtils;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"A custom string", "Returns: string"})
public class ExprString extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new InputParameter());
    }

    @Override
    public String toJava() {
        return "\"" + StringEscapeUtils.escapeJava(arg(0)) + "\"";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
