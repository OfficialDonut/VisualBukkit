package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Replaces all occurrences of a regex in a string with another string", "Returns: string"})
public class ExprRegexReplace extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("regex replace all", String.class, "in", String.class, "with", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".replaceAll(" + arg(0) + "," + arg(2) + ")";
    }
}
