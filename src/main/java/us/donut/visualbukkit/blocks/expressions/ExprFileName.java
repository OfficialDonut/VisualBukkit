package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description({"The name of a file (includes extension)", "Returns: string"})
public class ExprFileName extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("name of", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}
