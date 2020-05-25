package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Player IP")
@Category("Player")
@Description({"The IP of a player", "Returns: string"})
public class ExprPlayerIP extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("IP of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getAddress().getHostName()";
    }
}
