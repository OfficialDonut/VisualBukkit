package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

import java.io.File;

@Description({"The files in a directory", "Returns: list of files"})
public class ExprDirectoryFiles extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("files in directory", File.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".listFiles())";
    }
}
