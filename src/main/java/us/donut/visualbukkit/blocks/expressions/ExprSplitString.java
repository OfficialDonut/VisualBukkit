package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("String")
@Description({"Splits a string at every occurrence of another string", "Returns: list of strings"})
public class ExprSplitString extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "split at", String.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".split(" + arg(1) + "))";
    }
}
