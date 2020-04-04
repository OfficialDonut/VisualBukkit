package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The size of a list", "Returns: number"})
public class ExprListSize extends ExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("size of", SimpleList.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".size()";
    }
}
