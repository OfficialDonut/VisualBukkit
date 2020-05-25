package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"An argument of a command", "Returns: string"})
public class ExprCommandArg extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("command arg", int.class);
    }

    @Override
    public String toJava() {
        return "commandArgs[" + arg(0) + "]";
    }
}
