package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The name of a player", "Returns: string"})
public class ExprPlayerName extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("name of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
