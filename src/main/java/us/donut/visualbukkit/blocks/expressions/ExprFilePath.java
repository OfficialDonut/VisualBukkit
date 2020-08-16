package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description({"The path of a file", "Returns: string"})
public class ExprFilePath extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("path of", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCanonicalPath()";
    }
}
