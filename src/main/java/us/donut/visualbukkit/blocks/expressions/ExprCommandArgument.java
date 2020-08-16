package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An argument of a command", "Returns: string"})
public class ExprCommandArgument extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("argument", int.class);
    }

    @Override
    public String toJava() {
        return "commandArgs[" + arg(0) + "]";
    }
}
