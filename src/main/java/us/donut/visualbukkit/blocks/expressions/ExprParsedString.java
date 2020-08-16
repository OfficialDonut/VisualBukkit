package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Converts a string to a type", "Returns: object"})
public class ExprParsedString extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "parsed as", new ChoiceParameter(TypeHandler.getStringParsers().keySet()));
    }

    @Override
    public String toJava() {
        return TypeHandler.getStringParsers().get(arg(1)).apply(arg(0));
    }
}
