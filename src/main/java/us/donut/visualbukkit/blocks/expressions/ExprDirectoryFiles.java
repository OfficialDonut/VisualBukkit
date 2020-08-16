package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.io.File;
import java.util.List;

@Description({"The files in a directory", "Returns: list of files"})
public class ExprDirectoryFiles extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("files in directory", File.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".listFiles())";
    }
}
