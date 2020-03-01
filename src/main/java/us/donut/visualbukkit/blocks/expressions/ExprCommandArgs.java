package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ListExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The arguments of the command", "Returns: list"})
public class ExprCommandArgs extends ListExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("command args");
    }

    @Override
    public String toJava() {
        return "commandArgs";
    }

    @Override
    public Class<?> getReturnType() {
        return SimpleList.class;
    }
}
