package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description({"The parent file of a file", "Returns: file"})
public class ExprFileParent extends ExpressionBlock<File> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("parent file of", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getParentFile()";
    }
}
