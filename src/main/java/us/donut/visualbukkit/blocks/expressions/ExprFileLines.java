package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.io.File;
import java.util.List;

@Description({"The lines of a file", "Returns: list of strings"})
public class ExprFileLines extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("lines of", File.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(Files.readAllLines(" + arg(0) + ".toPath(),java.nio.charset.StandardCharsets.UTF_8))";
    }
}
