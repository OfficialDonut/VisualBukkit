package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;

@Description({"Represents a file (it may or may not exist)", "Returns: file"})
public class ExprFile extends ExpressionBlock<File> {

    @Override
    protected Syntax init() {
        return new Syntax("file", String.class);
    }

    @Override
    public String toJava() {
        return "new File(" + arg(0) + ")";
    }
}
