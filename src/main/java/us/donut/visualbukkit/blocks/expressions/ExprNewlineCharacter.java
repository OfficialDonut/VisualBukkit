package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The newline character", "Returns: string"})
public class ExprNewlineCharacter extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("newline");
    }

    @Override
    public String toJava() {
        return "\"\\n\"";
    }
}
