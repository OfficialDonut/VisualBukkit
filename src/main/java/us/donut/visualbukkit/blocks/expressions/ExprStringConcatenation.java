package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Combine Strings")
@Description({"Combines two strings together", "Returns: string"})
public class ExprStringConcatenation extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "+", String.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "+" + arg(1) + ")";
    }
}
