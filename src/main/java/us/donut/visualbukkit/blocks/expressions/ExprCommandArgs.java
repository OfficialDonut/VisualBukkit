package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"The arguments of a command", "Returns: list of strings"})
public class ExprCommandArgs extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("command args");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(commandArgs)";
    }
}
