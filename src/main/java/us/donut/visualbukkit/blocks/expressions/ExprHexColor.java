package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A hex color code", "Returns: string"})
public class ExprHexColor extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hex color", String.class);
    }

    @Override
    public String toJava() {
        return "net.md_5.bungee.api.ChatColor.of(" + arg(0) + ").toString()";
    }
}
