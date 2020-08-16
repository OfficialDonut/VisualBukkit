package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The arguments of a command", "Returns: list of strings"})
public class ExprCommandArguments extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("arguments");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(commandArgs)";
    }
}
