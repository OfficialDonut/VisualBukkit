package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.file.FileConfiguration;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A string representation of a config", "Returns: string"})
public class ExprConfigAsString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(FileConfiguration.class, "as string");
    }

    @Override
    public String toJava() {
        return arg(0) + ".saveToString()";
    }
}
