package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The newline character", "Returns: string"})
public class ExprNewlineCharacter extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("newline");
    }

    @Override
    public String toJava() {
        return "\"\\n\"";
    }
}
