package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Colors a string", "Returns: string"})
public class ExprColoredString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "colored");
    }

    @Override
    public String toJava() {
        return "PluginMain.color(" + arg(0) + ")";
    }
}
