package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The character at a position in a string", "Returns: string"})
public class ExprStringCharacter extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("character at position", int.class, "in", String.class);
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(1) + ".charAt(" + arg(0) + "))";
    }
}
