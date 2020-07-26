package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"All the keys in a config", "Returns: list of strings"})
public class ExprConfigKeys extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("keys of", ConfigurationSection.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getKeys(false))";
    }
}
