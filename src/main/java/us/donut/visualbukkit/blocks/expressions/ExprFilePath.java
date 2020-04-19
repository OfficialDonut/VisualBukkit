package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;

@Description({"The path of a file", "Returns: string"})
public class ExprFilePath extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("path of", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCanonicalPath()";
    }
}
