package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A hex color code", "Returns: string"})
public class ExprHexColor extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("hex color", String.class);
    }

    @Override
    public String toJava() {
        return "net.md_5.bungee.api.ChatColor.of(" + arg(0) + ").toString()";
    }
}
