package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Colors a string", "Returns: string"})
public class ExprColoredString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "colored");
    }

    @Override
    public String toJava() {
        return "PluginMain.color(" + arg(0) + ")";
    }
}
