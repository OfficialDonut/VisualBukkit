package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"All online players", "Returns: list of players"})
public class ExprOnlinePlayers extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("online players");
    }

    @Override
    public String toJava() {
        return "new SimpleList(Bukkit.getOnlinePlayers())";
    }
}
