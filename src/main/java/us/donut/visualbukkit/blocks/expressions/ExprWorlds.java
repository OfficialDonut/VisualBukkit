package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"All worlds", "Returns: list of worlds"})
public class ExprWorlds extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("worlds");
    }

    @Override
    public String toJava() {
        return "new SimpleList(Bukkit.getWorlds())";
    }
}
