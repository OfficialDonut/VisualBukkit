package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"All worlds", "Returns: list of worlds"})
public class ExprWorlds extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("worlds");
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(Bukkit.getWorlds())";
    }
}
